package com.nekovih.application;

import java.awt.*;

public interface IApplication extends Runnable {

    public void Start();
    public void Stop();

    public Dimension GetWindowSize();

    public Point GetCenterPoint();
}
