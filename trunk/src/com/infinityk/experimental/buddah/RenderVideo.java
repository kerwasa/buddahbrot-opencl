/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.infinityk.experimental.buddah;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.lwjgl.LWJGLException;
import org.lwjgl.opencl.CL;
import org.lwjgl.opencl.CL10;
import org.lwjgl.opencl.CLContext;
import org.lwjgl.opencl.CLDevice;
import org.lwjgl.opencl.CLPlatform;

/**
 *
 * @author asier
 */
public class RenderVideo {
    private static int frame = 0;
    
    //hardcoded for at least 2 teslas
    public static void main(String[] args) throws IOException{
         try {
            CL.create();
            CLPlatform platform = CLPlatform.getPlatforms().get(0);
            List<CLDevice> devices = platform.getDevices(CL10.CL_DEVICE_TYPE_GPU);
            CLContext context = CLContext.create(platform, devices, null, null, null);
            
            List<CLDevice> teslas = new ArrayList<CLDevice>();
            for( CLDevice d : devices){
                if (d.getInfoString(CL10.CL_DEVICE_NAME).toLowerCase().contains("tesla")){
                    teslas.add(d);
                }
            }
            
            frame = 0;
            BuddahBrot bb = generateFirstPart(context, teslas);
            generateSecondPart(bb,context, teslas);     bb = null;
            generateThirdPart(context,teslas);
            generateFourthPart(context,teslas);
            
        } catch (LWJGLException ex) {
            Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static BuddahBrot generateFirstPart(CLContext context, List<CLDevice> teslas) throws IOException {
        //generate first
        BuddahBrot bb = new BuddahBrot(
                0, 100, 
                100, 1000, 
                1000, 10000, 
                "Identity", 1.0f, 0.0f, 
                2.0f, 
                512, 32, 
                640, 480, 
                context, teslas.get(0));
        bb.calcDensityMaps();
        
        for(int i = 0; i < 60; i++){
            bb.setOverexposure(mix(0.0f, 0.9f, i/60.0f));
            bb.applyTransferFunctions();
            bb.saveImage("frame_1_" + pad(frame) + ".png");
            frame++;
        }
        for(int i = 0; i < 20; i++){
            bb.setOverexposure(mix(0.9f, 1.0f, i/20.0f));
            bb.applyTransferFunctions();
            bb.saveImage("frame_1_" + pad(frame) + ".png");
            frame++;
        }
        for(int i = 0; i < 60; i++){
            bb.setOverexposure(mix(1.0f, 1.9f, i/60.0f));
            bb.applyTransferFunctions();
            bb.saveImage("frame_1_" + pad(frame) + ".png");
            frame++;
        }
        for(int i = 0; i < 20; i++){
            bb.setOverexposure(mix(1.9f, 2.0f, i/20.0f));
            bb.applyTransferFunctions();
            bb.saveImage("frame_1_" + pad(frame) + ".png");
            frame++;
        }
        for(int i = 0; i < 60; i++){
            bb.setOverexposure(mix(2.0f, 2.9f, i/60.0f));
            bb.applyTransferFunctions();
            bb.saveImage("frame_1_" + pad(frame) + ".png");
            frame++;
        }
        for(int i = 0; i < 20; i++){
            bb.setOverexposure(mix(2.9f, 3.0f, i/20.0f));
            bb.applyTransferFunctions();
            bb.saveImage("frame_1_" + pad(frame) + ".png");
            frame++;
        }
        for(int i = 0; i < 60; i++){
            bb.setOverexposure(mix(3.0f, 3.9f, i/60.0f));
            bb.applyTransferFunctions();
            bb.saveImage("frame_1_" + pad(frame) + ".png");
            frame++;
        }
        for(int i = 0; i < 20; i++){
            bb.setOverexposure(mix(3.9f, 4.0f, i/20.0f));
            bb.applyTransferFunctions();
            bb.saveImage("frame_1_" + pad(frame) + ".png");
            frame++;
        }
        
        return bb;
    }
    
    
    private static void generateSecondPart(BuddahBrot bb, CLContext context, List<CLDevice> teslas) throws IOException {        
        bb.setOverexposure(1.0f);
        bb.setTransferFunction("Logarithmic");
        for(int i = 0; i < 160; i++){
            bb.setFactor(mix(1.0f, 33.0f, i/160.0f));
            bb.applyTransferFunctions();
            bb.saveImage("frame_2_" + pad(frame) + ".png");
            frame++;
        }
        
        bb.setTransferFunction("Exponential");
        for(int i = 0; i < 160; i++){
            bb.setFactor(mix(1.0f, 33.0f, i/160.0f));
            bb.applyTransferFunctions();
            bb.saveImage("frame_2_" + pad(frame) + ".png");
            frame++;
        }
       
    }
    
    

    private static void generateThirdPart(CLContext context, List<CLDevice> teslas) {
        final BuddahBrot bb1 = new BuddahBrot(
                10000, 100000, 
                0, 0, 
                0, 0, 
                "Logarithmic", 5.0f, 1.0f, 
                2.0f, 
                128, 32, 
                640, 480, 
                context, teslas.get(0));
        final BuddahBrot bb2 = new BuddahBrot(
                10000, 100000, 
                0, 0, 
                0, 0,  
                "Logarithmic", 5.0f, 1.0f, 
                2.0f, 
                128, 32, 
                640, 480, 
                context, teslas.get(1));
        for(int i = 0; i < 160; i+=2){
            int i1 = i;
            int i2 = i+1;
            
            bb1.setRedMin( (int)mix(5000, 20000, i1/160.0f) );
            bb2.setRedMin( (int)mix(5000, 20000, i2/160.0f) );
            bb1.setRedMax( (int)mix(50000, 200000, i1/160.0f) );
            bb2.setRedMax( (int)mix(50000, 200000, i2/160.0f) );
            Thread t1 = new Thread(new Runnable() {
                @Override
                public void run() {
                    bb1.calcDensityMaps();
                    bb1.applyTransferFunctions();
                    try {
                        bb1.saveImage("frame_3_" + pad(frame) + ".png");
                    } catch (IOException ex) {
                        Logger.getLogger(RenderVideo.class.getName()).log(Level.SEVERE, null, ex);
                    }
                   
                }
            });
            
            Thread t2 = new Thread(new Runnable() {
                @Override
                public void run() {
                    bb2.calcDensityMaps();
                    bb2.applyTransferFunctions();
                    try {
                        bb2.saveImage("frame_3_" + pad(frame+1) + ".png");
                    } catch (IOException ex) {
                        Logger.getLogger(RenderVideo.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    
                  
                }
            });
            
            t1.start();
            t2.start();
            try {
                t1.join();
            } catch (InterruptedException ex) {
                Logger.getLogger(RenderVideo.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                t2.join();
            } catch (InterruptedException ex) {
                Logger.getLogger(RenderVideo.class.getName()).log(Level.SEVERE, null, ex);
            }
            frame += 2;
        }
 
    }

    private static void generateFourthPart(CLContext context, List<CLDevice> teslas) {
        final BuddahBrot bb1 = new BuddahBrot(
                0, 10, 
                10, 100, 
                100, 1000, 
                "Identity", 1.0f, 2.0f, 
                2.0f, 
                128, 32, 
                640, 480, 
                context, teslas.get(0));
        final BuddahBrot bb2 = new BuddahBrot(
                0, 10, 
                10, 100, 
                100, 1000, 
                "Identity", 1.0f, 2.0f, 
                2.0f, 
                128, 32, 
                640, 480, 
                context, teslas.get(1));
        
        for(int i = 0; i < 80; i+=2){
            int i1 = i;
            int i2 = i+1;
           
            bb1.setExponent( mix(2, 1, i1/80.0f) );
            bb2.setExponent( mix(2, 1, i2/80.0f) );
            Thread t1 = new Thread(new Runnable() {
                @Override
                public void run() {
                    bb1.calcDensityMaps();
                    bb1.applyTransferFunctions();
                    try {
                        bb1.saveImage("frame_4_" + pad(frame) + ".png");
                    } catch (IOException ex) {
                        Logger.getLogger(RenderVideo.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            });
            
            Thread t2 = new Thread(new Runnable() {
                @Override
                public void run() {
                    bb2.calcDensityMaps();
                    bb2.applyTransferFunctions();
                    try {
                        bb2.saveImage("frame_4_" + pad(frame+1) + ".png");
                    } catch (IOException ex) {
                        Logger.getLogger(RenderVideo.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            });
            
            t1.start();
            t2.start();
            try {
                t1.join();
            } catch (InterruptedException ex) {
                Logger.getLogger(RenderVideo.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                t2.join();
            } catch (InterruptedException ex) {
                Logger.getLogger(RenderVideo.class.getName()).log(Level.SEVERE, null, ex);
            }
            frame += 2;
        }
        
        for(int i = 0; i < 160; i+=2){
            int i1 = i;
            int i2 = i+1;
           
            bb1.setExponent( mix(1, 5, i1/160.0f) );
            bb2.setExponent( mix(1, 5, i2/160.0f) );
            Thread t1 = new Thread(new Runnable() {
                @Override
                public void run() {
                    bb1.calcDensityMaps();
                    bb1.applyTransferFunctions();
                    try {
                        bb1.saveImage("frame_4_" + pad(frame)+ ".png");
                    } catch (IOException ex) {
                        Logger.getLogger(RenderVideo.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            });
            
            Thread t2 = new Thread(new Runnable() {
                @Override
                public void run() {
                    bb2.calcDensityMaps();
                    bb2.applyTransferFunctions();
                    try {
                        bb2.saveImage("frame_4_" + pad(frame+1) + ".png");
                    } catch (IOException ex) {
                        Logger.getLogger(RenderVideo.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            });
            
            t1.start();
            t2.start();
            try {
                t1.join();
            } catch (InterruptedException ex) {
                Logger.getLogger(RenderVideo.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                t2.join();
            } catch (InterruptedException ex) {
                Logger.getLogger(RenderVideo.class.getName()).log(Level.SEVERE, null, ex);
            }
            frame += 2;
        }
        
        for(int i = 0; i < 80; i+=2){
            int i1 = i;
            int i2 = i+1;
           
            bb1.setExponent( mix(5, 21, i1/80.0f) );
            bb2.setExponent( mix(5, 21, i2/80.0f) );
            Thread t1 = new Thread(new Runnable() {
                @Override
                public void run() {
                    bb1.calcDensityMaps();
                    bb1.applyTransferFunctions();
                    try {
                        bb1.saveImage("frame_4_" + pad(frame) + ".png");
                    } catch (IOException ex) {
                        Logger.getLogger(RenderVideo.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            });
            
            Thread t2 = new Thread(new Runnable() {
                @Override
                public void run() {
                    bb2.calcDensityMaps();
                    bb2.applyTransferFunctions();
                    try {
                        bb2.saveImage("frame_4_" + pad(frame+1) + ".png");
                    } catch (IOException ex) {
                        Logger.getLogger(RenderVideo.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            });
            
            t1.start();
            t2.start();
            try {
                t1.join();
            } catch (InterruptedException ex) {
                Logger.getLogger(RenderVideo.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                t2.join();
            } catch (InterruptedException ex) {
                Logger.getLogger(RenderVideo.class.getName()).log(Level.SEVERE, null, ex);
            }
            frame += 2;
        }
    }
    
    public static String pad(int n){
        String forReturn = n + "";
        for(int i = forReturn.length(); i < 6; i++){
            forReturn = "0" + forReturn;
        }
        return forReturn;
    }
    
    public static float mix(float a, float b, float t){
        return a + (b-a) * t;
    }
   
}
