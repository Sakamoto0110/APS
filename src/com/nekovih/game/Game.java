package com.nekovih.game;

import com.nekovih.application.Application;
import com.nekovih.application.IApplication;

import java.awt.*;


public class Game implements  IGameObject {

    public Ball ball;

    @Override
    public void Tick() {
       ball.Tick();;
    }

    @Override
    public void Render(Graphics g) {
        ball.Render(g);
    }

    public class Ball implements IGameObject  {
        public int m_x,  m_y;
        public int m_dx, m_dy;
        public Color m_Color;
        public int m_Radius;
        public Ball(int x, int y, int radius, int dx, int dy, Color color) {
            this.m_x = x;
            this.m_y = y;
            this.m_Radius = radius;
            this.m_dx = dx;
            this.m_dy = dy;
            this.m_Color = color;

        }


        @Override
        public void Render(Graphics g) {
            g.setColor(m_Color);
            g.fillOval(m_x, m_y, m_Radius*2, m_Radius*2);
        }

        @Override
        public void Tick() {
            m_x += m_dx;
            m_y += m_dy;
            IApplication app =  Application.GetInstance();
            Dimension bounds = app.GetWindowSize();
            if(m_x+m_Radius >= bounds.width || m_x-m_Radius <= 0){
                m_dx *= -1;
            }
            if(m_y+m_Radius >= bounds.height || m_y-m_Radius <= 0){
                m_dy *= -1;
            }


        }


    }


    public Game(){
         IApplication app =  Application.GetInstance();

         ball = new Ball(app.GetCenterPoint().x,app.GetCenterPoint().y,50,1,1,Color.pink);
    }








}
