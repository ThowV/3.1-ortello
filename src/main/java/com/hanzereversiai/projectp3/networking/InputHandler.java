package com.hanzereversiai.projectp3.networking;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;

public class InputHandler implements Runnable {

    private BufferedReader reader;
    private ArrayList<InputListener> inputListeners;
    private DebugHandler debugHandler;

    public InputHandler(BufferedReader reader) {
        this.reader = reader;
        this.inputListeners = new ArrayList<InputListener>();

        debugHandler = new DebugHandler();
        this.Subscribe(debugHandler);
    }

    @Override
    public void run() {
        while(true) {
            try {
                String input = reader.readLine();
                for (InputListener inputListener: inputListeners) {
                    inputListener.handleInput(input);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void Subscribe(InputListener inputListener) {
        inputListeners.add(inputListener);
    }

    private class DebugHandler implements InputListener {
        @Override
        public void handleInput(String input) {
            System.out.println("RECEIVED: " + input);
        }
    }
}

