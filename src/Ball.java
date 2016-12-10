package futbol;

import javafx.scene.shape.Circle;
import java.awt.*;

class Ball extends Circle{
    double xPos;
    double yPos;
    double xSpeed = 0;
    double ySpeed = 0;
    final int SIZE=15;
    double slow=1.5;
	double slowX=0;
	double slowY=0;
    Thread ballMover=new Thread(new Runnable(){
        @Override
        public void run(){
            while(true){
				
				//Ball bounce
                if((getCenterX()<=SIZE)&&xSpeed<0){
                    xSpeed=-xSpeed+0.2;
					System.out.println("bounce");
				}
				else if(xSpeed>0&&(getCenterX()>=Main.WIDTH-SIZE*2-20)){
                    xSpeed=-xSpeed-0.2;
					System.out.println("bounce");
				}
                if((getCenterY()<=SIZE)&&ySpeed<0){
                    ySpeed=-ySpeed+0.2;
					System.out.println("bounce");
				}
				else if(ySpeed>0&&(getCenterY()>=Main.HEIGHT-SIZE*2-20)){
                    ySpeed=-ySpeed-0.2;
					System.out.println("bounce");
				}
				
				//Ball move
                setCenterX(getCenterX() + xSpeed);
                setCenterY(getCenterY() + ySpeed);               
                
                //Ball slows over time
				double moveAngle;
				moveAngle=Math.atan2(ySpeed,xSpeed);
				slowX=slow*xSpeed/(xSpeed+ySpeed);
				slowY=slow*ySpeed/(xSpeed+ySpeed);
				
				
                if(xSpeed>=slowX)
                    xSpeed -=slowX;
                else if(xSpeed<=-slowX)
                    xSpeed +=slowX;
                else xSpeed=0;
                if(ySpeed>=slowY)
                    ySpeed -= slowY;
                else if(ySpeed<=-slowY)
                    ySpeed += slowY;
                else ySpeed=0;
                    
                try{
                    Thread.sleep(16);
                }catch(InterruptedException e){
                    e.printStackTrace();
                }
            }
        }
    });

    Ball(){
        xPos = Main.WIDTH/2;
        yPos = Main.HEIGHT/2;

        setCenterX(xPos);
        setCenterY(yPos);
    }

    void draw(Graphics g){
        g.setColor(Color.green);

        xPos = getCenterX();
        yPos = getCenterY();

        g.fillOval((int)xPos,(int)yPos,SIZE,SIZE);
    }

    void hit(double xPos,double yPos,double xSpeed,double ySpeed){
        double ydist = yPos-this.yPos;
        double xdist = xPos-this.xPos;
        this.xSpeed = xSpeed/2;
        this.ySpeed = ySpeed/2;
        double angle = Math.atan2(ydist,xdist);
        double speed = Math.sqrt(ydist*ydist+xdist*xdist);
        this.xSpeed = -speed*Math.cos(angle);
		this.ySpeed = -speed*Math.sin(angle);
        this.xSpeed += xSpeed/2;	//Bu iki satırın tekrar eklenmesi top ve oyuncu arasındaki açının yanı sıra
        this.ySpeed += ySpeed/2;	//topun oyuncunun hareket yönüne doğru da hareket etmesini sağlar
    }
}
