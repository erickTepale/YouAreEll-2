package views;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import controllers.IdController;
import controllers.MessageController;
import models.Id;
import okhttp3.*;

// Simple Shell is a Console view for views.YouAreEll.
public class SimpleShell {


    public static void prettyPrint(String output) {
        // yep, make an effort to format things nicely, eh?
        //System.out.println(output);
    }

    // returns a json string of a new Id variable
    public static String parseIdInput(String idInput) throws JsonProcessingException {
        String[] data = idInput.split(" ");
        return new ObjectMapper().writeValueAsString(new Id(data[0], data[1]));
    }

    public static String validatePostId(String idInput) throws JsonProcessingException {
        String[] data = idInput.split(" ");
        IdController controller = IdController.getInstance();

        for (Id each : controller.getIdList()) {
            if(data[1].equals(each.getGithub())){
                System.out.println("Identity already exists; ");
                return null;
            }
        }
        return parseIdInput(idInput);
    }

    public static void main(String[] args) throws java.io.IOException {
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        YouAreEll webber = new YouAreEll(MessageController.getInstance(), IdController.getInstance());
        
        String commandLine;
        BufferedReader console = new BufferedReader
                (new InputStreamReader(System.in));

        ProcessBuilder pb = new ProcessBuilder();
        List<String> history = new ArrayList<String>();
        int index = 0;
        //we break out with <ctrl c>
        while (true) {
            //read what the user enters
            System.out.println("cmd? ");
            commandLine = console.readLine();

            //input parsed into array of strings(command and arguments)
            String[] commands = commandLine.split(" ");
            List<String> list = new ArrayList<String>();

            //if the user entered a return, just loop again
            if (commandLine.equals(""))
                continue;
            else if(commandLine.equals("ids")){
                webber.get_ids();
                IdController.getInstance().getIdList().stream().forEach(elem->System.out.println(elem+" "));
            }else if(commandLine.equals("messages")){
               webber.get_messages();
                MessageController.getInstance().getMessages().stream().forEach(elem->System.out.println(elem+" "));
            }else if(commandLine.equals("updateID")){
                webber.get_ids();
                System.out.println("Input: {name} {github username}");
                webber.putId(parseIdInput(console.readLine()));
            }else if (commandLine.equals("postID")){
                webber.get_ids();
                System.out.println("Input: {name} {github username}");

                webber.postId(validatePostId(console.readLine()));

            }else if (commandLine.equals("exit")) {
                System.out.println("bye!");
                break;
            }

            //loop through to see if parsing worked
            for (int i = 0; i < commands.length; i++) {
                //System.out.println(commands[i]); //***check to see if parsing/split worked***
                list.add(commands[i]);

            }
            System.out.print(list); //***check to see if list was added correctly***
            history.addAll(list);
            try {
                //display history of shell with index
                if (list.get(list.size() - 1).equals("history")) {
                    for (String s : history)
                        System.out.println((index++) + " " + s);
                    continue;
                }

                // Specific Commands.

                // ids
                if (list.contains("ids")) {
                    String results = webber.get_ids();
                    SimpleShell.prettyPrint(results);
                    continue;
                }

                // messages
                if (list.contains("messages")) {
                    String results = webber.get_messages();
                    SimpleShell.prettyPrint(results);
                    continue;
                }
                // you need to add a bunch more.

                //!! command returns the last command in history
                if (list.get(list.size() - 1).equals("!!")) {
                    pb.command(history.get(history.size() - 2));

                }//!<integer value i> command
                else if (list.get(list.size() - 1).charAt(0) == '!') {
                    int b = Character.getNumericValue(list.get(list.size() - 1).charAt(1));
                    if (b <= history.size())//check if integer entered isn't bigger than history size
                        pb.command(history.get(b));
                } else {
                    pb.command(list);
                }

                // wait, wait, what curiousness is this?
                Process process = pb.start();

                //obtain the input stream
                InputStream is = process.getInputStream();
                InputStreamReader isr = new InputStreamReader(is);
                BufferedReader br = new BufferedReader(isr);

                //read output of the process
                String line;
                while ((line = br.readLine()) != null)
                    System.out.println(line);
                br.close();
            }

            //catch ioexception, output appropriate message, resume waiting for input
            catch (IOException e) {
                System.out.println("Input Error, Please try again!");
            }
            // So what, do you suppose, is the meaning of this comment?
            /** The steps are:
             * 1. parse the input to obtain the command and any parameters
             * 2. create a ProcessBuilder object
             * 3. start the process
             * 4. obtain the output stream
             * 5. output the contents returned by the command
             */
        }


    }

}