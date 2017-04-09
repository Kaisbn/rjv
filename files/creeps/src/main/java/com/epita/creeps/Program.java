package com.epita.creeps;


public class Program {

    public static void main(String... args) {
        if (args.length != 3) {
            throw new IllegalArgumentException("not enough arguments");
        }

        String hostname = args[0];
        String port = args[1];
        String login = args[2];

        System.out.printf("hostname: %s, port: %s, login: %s\n", hostname, port, login);
    }
}
