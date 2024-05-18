package com.nekovih.application;

import com.nekovih.game.Game;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

/*
m_CamelCase -> public member
g_CamelCase -> static/global
_CamelCase  -> private member

 */

public class Application extends Canvas implements IApplication {
    private static IApplication g_Instance;
    public static IApplication GetInstance() {
        return g_Instance;
    }

    private JFrame        _Frame;
    private Thread        _Thread;
    private BufferedImage _Background;
    private boolean       _IsRunning;
    private Game _Game;

      private Application(int width, int height, String title){
          setPreferredSize(new Dimension(width, height));
          _IsRunning = false;
          _Frame = new JFrame(title);
          _Frame.setSize(width, height);
          _Frame.add(this);
          _Frame.setResizable(false);
          _Frame.pack();
          _Frame.setLocationRelativeTo(null);
          _Frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
          _Frame.setVisible(true);
          _Thread = new Thread(this);
          _Background = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);



    }




    public static IApplication Initialize(int width, int height, String title){
        if(g_Instance == null){
            g_Instance = new Application(width, height, title);
        }
        return g_Instance;
    }

    @Override
    public synchronized void Start() {
        _IsRunning = true;
        _Game = new Game();
        _Thread.start();
    }

    @SuppressWarnings("CallToPrintStackTrace")
    @Override
    public synchronized void Stop() {
       _IsRunning = false;
        try {
            _Thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Dimension GetWindowSize() {
        return new Dimension(this.getWidth(),this.getHeight());
    }

    @Override
    public Point GetCenterPoint() {
          return new Point(GetWindowSize().width/2,GetWindowSize().height/2);
    }


    @Override
    public void run() {
        long lastTime = System.nanoTime();
        double amoutOfTicks = 60.0;
        double ns = 1000000000 / amoutOfTicks;
        double delta = 0;
        int frames = 0;
        double timer = System.currentTimeMillis();
        while(_IsRunning)
        {
            long now = System.nanoTime();
            delta+= (now-lastTime) / ns;
            lastTime = now;
            if ( delta >= 1 )
            {
                this.Tick();
                this.Render();
                frames++;
                delta--;
            }
            if ( System.currentTimeMillis()-timer >= 1000)
            {
                System.out.println("FPS: "+frames);
                frames = 0;
                timer+= 1000;;
            }
        }
        this.Stop();
    }


    private void Tick(){
        _Game.Tick();
    }

    private void Render(){
        BufferStrategy bs = getBufferStrategy();
        if ( bs == null ) {
            createBufferStrategy(3);
            return;
        }
        Graphics g = _Background.getGraphics();
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setColor(new Color(11,11,11));
        g.fillRect(0, 0, getWidth(), getHeight());
        // **
        _Game.Render(g);


        // **
        g.dispose();
        g = bs.getDrawGraphics();
        g.drawImage(_Background, 0, 0, getWidth(), getHeight(),null);
        bs.show();
    }


}
