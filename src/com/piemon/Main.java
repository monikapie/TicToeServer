package com.piemon;

import com.piemon.tictoe.GameConnector;
import com.piemon.tictoe.TicToeConnector;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class Main {

    public static void main(String[] args) {
        System.setProperty("java.rmi.server.hostname", "127.0.0.1");
        try {
            GameConnector ticToeConnector = new TicToeConnector();
            GameConnector gameConnector = (GameConnector) UnicastRemoteObject.exportObject(ticToeConnector, 0);
            Registry registry = LocateRegistry.createRegistry(9000);
            registry.rebind("TicTacToe", gameConnector);
            System.out.println("TicTacToe is bound.");
        } catch (RemoteException e) {
            System.out.println("TicTacToe is not bound. RemoteException appeared: " + e.toString());
        }
    }
}
