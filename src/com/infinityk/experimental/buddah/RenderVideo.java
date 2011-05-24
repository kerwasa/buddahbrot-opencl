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
            
            BuddahBrot bb = generateFirstPart(context, teslas);
            generateSecondPart(bb,context, teslas);
            
            
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
            bb.saveImage("frame_1_" + pad(frame));
            frame++;
        }
        for(int i = 0; i < 20; i++){
            bb.setOverexposure(mix(0.9f, 1.0f, i/20.0f));
            bb.applyTransferFunctions();
            bb.saveImage("frame_1_" + pad(frame));
            frame++;
        }
        for(int i = 0; i < 60; i++){
            bb.setOverexposure(mix(1.0f, 1.9f, i/60.0f));
            bb.applyTransferFunctions();
            bb.saveImage("frame_1_" + pad(frame));
            frame++;
        }
        for(int i = 0; i < 20; i++){
            bb.setOverexposure(mix(1.9f, 2.0f, i/20.0f));
            bb.applyTransferFunctions();
            bb.saveImage("frame_1_" + pad(frame));
            frame++;
        }
        for(int i = 0; i < 60; i++){
            bb.setOverexposure(mix(2.0f, 2.9f, i/60.0f));
            bb.applyTransferFunctions();
            bb.saveImage("frame_1_" + pad(frame));
            frame++;
        }
        for(int i = 0; i < 20; i++){
            bb.setOverexposure(mix(2.9f, 3.0f, i/20.0f));
            bb.applyTransferFunctions();
            bb.saveImage("frame_1_" + pad(frame));
            frame++;
        }
        for(int i = 0; i < 60; i++){
            bb.setOverexposure(mix(3.0f, 3.9f, i/60.0f));
            bb.applyTransferFunctions();
            bb.saveImage("frame_1_" + pad(frame));
            frame++;
        }
        for(int i = 0; i < 20; i++){
            bb.setOverexposure(mix(3.9f, 4.0f, i/20.0f));
            bb.applyTransferFunctions();
            bb.saveImage("frame_1_" + pad(frame));
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
            bb.saveImage("frame_2_" + pad(frame));
            frame++;
        }
        bb.setTransferFunction("Exponential");
        for(int i = 0; i < 160; i++){
            bb.setFactor(mix(1.0f, 33.0f, i/160.0f));
            bb.applyTransferFunctions();
            bb.saveImage("frame_2_" + pad(frame));
            frame++;
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
