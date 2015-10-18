package com.wanma.client.dev;
import com.google.gwt.canvas.dom.client.Context2d;

public class Connector {
	public int aX;
	public int aY;
	public int zX;
	public int zY;
    public int aHeight;
    public int aWidth;
    public int zHeight;
    public int zWidth;
    
    public DeviceIcon aDevice;
    public DeviceIcon zDevice;
    
	public int thick=1;  //�ߵĴ�ϸ	

	public double length;
	
	public Connector(DeviceIcon aDevice, DeviceIcon zDevice){
		this.aDevice=aDevice;
		this.zDevice=zDevice;
		aX=aDevice.x;
		aY=aDevice.y;
		zX=zDevice.x;
		zY=zDevice.y;
		aHeight=aDevice.height;
		aWidth=aDevice.width;
		zHeight=zDevice.height;
		zWidth=zDevice.width;
		length = Math.sqrt((aX-zX)^2+(aY-zY)^2);
	}
	
	public void drawSelf(Context2d context){
		context.beginPath();
    	//first judge the relative position of the two DeviceIcon, 
    	if (aX>=zX&&aY<=zY){  // a is at z's right top position
    		if ((aY>=zY+zHeight/2)&&(aX>=zX+zWidth/2)){
    			context.moveTo(zX+zWidth/2, zY);
    			context.lineTo(aX, aY+aHeight/2);
    		}
    		else if((aY<=zY-zHeight/2)&&(aX>=zX+zWidth)){
    			context.moveTo(zX+zWidth, zY+zHeight/2);
    			context.lineTo(aX, aY+aHeight/2);
    		}
    		else {
    			context.moveTo(zX+zWidth/2, zY);
    			context.lineTo(aX+aWidth/2,aY+aHeight);
    		}
    		
    	}
    	else if(zX>=aX&&zY<=aY){// z is at a's right top positon
    		if ((aY>=aY+zHeight/2)&&(zX>=aX+aWidth/2)){
    			context.moveTo(aX+aWidth/2, aY);
    			context.lineTo(zX, zY+aHeight/2);
    		}
    		else if((zY<=aY-aHeight/2)&&(zX>=aX+aWidth)){
    			context.moveTo(aX+aWidth, aY+aHeight/2);
    			context.lineTo(zX, zY+zHeight/2);
    		}
    		else {
    			context.moveTo(aX+aWidth/2, aY);
    			context.lineTo(zX+zWidth/2,zY+zHeight);
    		}	
    	}
    	else if((aX<=zX)&&(aY<=zY)){
    		if ((aY>=zY+zHeight/2)&&(aX<=zX-zWidth/2)){
    			context.moveTo(aX+aWidth, aY+aHeight/2);
    			context.lineTo(zX+zWidth/2,zY);
    		}
    		else if (aX>zX-zWidth/2){
    			context.moveTo(aX+aWidth/2, aY+aHeight);
    			context.lineTo(zX+zWidth/2, zY);
    		}
    		else{
    			context.moveTo(aX+aWidth, aY+aHeight/2);
    			context.lineTo(zX, zY+zHeight/2);
    			
    		}
    	
    	}
    	else{
    		
    		if ((zY>=aY+aHeight/2)&&(zX<=aX-aWidth/2)){
    			context.moveTo(zX+zWidth, zY+zHeight/2);
    			context.lineTo(aX+aWidth/2,aY);
    		}
    		else if (zX>aX-aWidth/2){
    			context.moveTo(zX+zWidth/2, zY+zHeight);
    			context.lineTo(aX+aWidth/2, aY);
    		}
    		else{
    			context.moveTo(zX+zWidth, zY+zHeight/2);
    			context.lineTo(aX, aY+aHeight/2);
    		}
    	}
    	context.setLineWidth(thick);
    	context.stroke();
	}
	
	
}

