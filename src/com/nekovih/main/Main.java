package com.nekovih.main;

import com.nekovih.application.Application;
import com.nekovih.application.IApplication;

public class Main {
    public static void main(String[] args) {

        IApplication app = Application.Initialize(720,480,"Kawaaaai :3");
        app.Start();

    }
}