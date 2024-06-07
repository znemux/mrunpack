package znemux.mrzip;

import znemux.mrzip.gui.Frame;

public class Main {
    
    public static Frame frame;
    static boolean gui;
    
    public static void main(String[] args) {
        // Checks if the program is executed with an input argument, this implies the
        // program is ran through command line and will not show a graphical interface
        gui = args.length != 1;
        
        if (gui) {
           Frame.main();
        } else {
            nogui(args[0]);
        }
    }
    
    static void nogui(String argument) {
        switch (argument) {
            case "-h":
            case "--help":
                System.out.println("    Usage:\n"
                        + "\tmrzip <mrpackfile> \t\tConverts the mrpack to a zip.\n"
                        + "\tmrzip -h (or --help)\t\tShows this.\n"
                        + "\tmrzip -r            \t\tChecks if the program works.\n"
                        + "\tmrzip (no arguments)\t\tLaunches the GUI application.");
                break;
            case "-r":
                System.out.println("Program works!");
                break;
            default:
                new Convert.thread(argument).start();
                break;
        }
    }
    
}
