/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.infinityk.experimental.buddah;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.IntBuffer;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import org.lwjgl.BufferUtils;
import org.lwjgl.PointerBuffer;
import org.lwjgl.opencl.CL10;
import org.lwjgl.opencl.CLCommandQueue;
import org.lwjgl.opencl.CLContext;
import org.lwjgl.opencl.CLDevice;
import org.lwjgl.opencl.CLKernel;
import org.lwjgl.opencl.CLMem;
import org.lwjgl.opencl.CLProgram;
import org.lwjgl.opencl.Util;


/**
 *
 * @author Asier
 */
public class BuddahBrot {
    private int redMin,redMax,greenMin,greenMax, blueMin, blueMax;
    private String transferFunction;
    private float factor;
    private float overexposure;
    private float exponent;
    private int samplesPerWorkingItemSide, workingItemsPerSide;
    private int imageWidth, imageHeight;
    
    private CLContext context;
    private CLDevice device;
    
    private IntBuffer redBuffer;
    private IntBuffer blueBuffer;
    private IntBuffer greenBuffer;
    private int[] argb;
    
    private boolean switchColorChannels;
    
    private double time;

    public double getTime() {
        return time;
    }
    
    
    
    public BuddahBrot(int redMin, int redMax, int greenMin, int greenMax, int blueMin, int blueMax, 
            String transferFunction, float factor, float overexposure, float exponent,
            int samplesPerWorkingItemSide, int workingItemsPerSide, 
            int imageWidth, int imageHeight,
            CLContext context,CLDevice device ) {
        this.redMin = redMin;
        this.redMax = redMax;
        this.greenMin = greenMin;
        this.greenMax = greenMax;
        this.blueMin = blueMin;
        this.blueMax = blueMax;
        this.exponent = exponent;
        this.transferFunction = transferFunction;
        this.factor = factor;
        this.overexposure = overexposure;
        this.samplesPerWorkingItemSide = samplesPerWorkingItemSide;
        this.workingItemsPerSide = workingItemsPerSide;
        this.imageWidth = imageWidth;
        this.imageHeight = imageHeight;
        this.context = context;
        this.device = device;
    }    
    
    
    private String getSourceCode(String fileName){
        URL url = getClass().getResource("/com/infinityk/experimental/buddah/" + fileName);
        try {
            try {
                return new String( FileUtils.getBytesFromFile(new File(url.toURI())));
            } catch (IOException ex) {
                Logger.getLogger(BuddahBrot.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (URISyntaxException ex) {
            Logger.getLogger(BuddahBrot.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public void calcDensityMaps() {
        System.out.println("Calc w=" 
                + samplesPerWorkingItemSide + " e=" + exponent 
                + " t=" + transferFunction + " f=" + factor 
                + " o=" + overexposure 
                + " c=" + redMin + "-" + redMax +  "," 
                + greenMin + "-" + greenMax + ","
                + blueMin + "-" + blueMax + ",");
        CLCommandQueue queue = CL10.clCreateCommandQueue(context, device, 0, null);

        time = System.currentTimeMillis();
        
        // allocation
        redBuffer = BufferUtils.createIntBuffer(imageWidth * imageHeight);
        greenBuffer = BufferUtils.createIntBuffer(imageWidth * imageHeight);
        blueBuffer = BufferUtils.createIntBuffer(imageWidth * imageHeight);
        
        CLMem red = CL10.clCreateBuffer(context, CL10.CL_MEM_WRITE_ONLY | CL10.CL_MEM_COPY_HOST_PTR, redBuffer, null);
        CLMem green = CL10.clCreateBuffer(context, CL10.CL_MEM_WRITE_ONLY | CL10.CL_MEM_COPY_HOST_PTR, greenBuffer, null);
        CLMem blue = CL10.clCreateBuffer(context, CL10.CL_MEM_WRITE_ONLY | CL10.CL_MEM_COPY_HOST_PTR, blueBuffer, null);
        
        // program/kernel creation
        String sourceFileName = exponent!=2.0f?"buddahWithNot2Exp.cl":"buddah.cl";
        CLProgram program = CL10.clCreateProgramWithSource(context, getSourceCode(sourceFileName), null);
        Util.checkCLError(CL10.clBuildProgram(program, device, "", null));
        CLKernel kernel = CL10.clCreateKernel(program, "buddah", null);

        // execution
        PointerBuffer sizeNxN = BufferUtils.createPointerBuffer(2);
        sizeNxN.put(0, workingItemsPerSide);
        sizeNxN.put(1, workingItemsPerSide);
        kernel.setArg(0, samplesPerWorkingItemSide);
        kernel.setArg(1, imageWidth);
        kernel.setArg(2, imageHeight);
        kernel.setArg(3, red);
        kernel.setArg(4, green);
        kernel.setArg(5, blue);
        kernel.setArg(6, redMin); kernel.setArg(7, redMax);
        kernel.setArg(8, greenMin); kernel.setArg(9, greenMax);
        kernel.setArg(10, blueMin); kernel.setArg(11, blueMax);
        kernel.setArg(12, Math.max(Math.max(redMax, greenMax), blueMax));
        if(exponent != 2.0f){
            kernel.setArg(13, exponent);
        }
        CL10.clEnqueueNDRangeKernel(queue, kernel, 2, null, sizeNxN, null, null, null);
        
        // read the results back
        CL10.clEnqueueReadBuffer(queue, red, 1, 0, redBuffer, null, null);
        CL10.clEnqueueReadBuffer(queue, green, 1, 0, greenBuffer, null, null);
        CL10.clEnqueueReadBuffer(queue, blue, 1, 0, blueBuffer, null, null);
        CL10.clFinish(queue);

        // teardown
        CL10.clReleaseMemObject(red);
        CL10.clReleaseMemObject(green);
        CL10.clReleaseMemObject(blue);
        CL10.clReleaseKernel(kernel);
        CL10.clReleaseProgram(program);
        CL10.clReleaseCommandQueue(queue);
        
        time = System.currentTimeMillis() - time;
        System.out.println("Millis: " + time);
    }

    public void applyTransferFunctions() {
        int nPixels = imageWidth * imageHeight;
        argb = new int[nPixels];
        float maxRed = 0;
        float maxGreen = 0;
        float maxBlue = 0;
        for(int i = 0; i < nPixels; ++i){
            maxRed = Math.max(maxRed, redBuffer.get(i));
            maxGreen = Math.max(maxGreen, greenBuffer.get(i));
            maxBlue = Math.max(maxBlue, blueBuffer.get(i));
        }
        System.out.println("red = " + maxRed + " green = " + maxGreen + " blue = " + maxBlue);
        
        int red,green,blue;
        //more compact with if inside but decrease performance
        if (transferFunction.equalsIgnoreCase("Exponential")){
            for(int i = 0; i < nPixels; ++i){
                red = (int)(255.0f * overexposure * (1.0f - Math.exp(-redBuffer.get(i) / maxRed * factor) ) );
                green = (int)(255.0f * overexposure * (1.0f - Math.exp(-greenBuffer.get(i) / maxGreen * factor) ) );
                blue = (int)(255.0f * overexposure * (1.0f - Math.exp(-blueBuffer.get(i) / maxBlue * factor) ) );
                
                red = Math.min(Math.max(red, 0),255);
                green = Math.min(Math.max(green, 0),255);
                blue = Math.min(Math.max(blue, 0),255);
                if(switchColorChannels){
                    int temp = red;
                    red = green;
                    green = blue;
                    blue = temp;
                }
                
                argb[i] = ((0xFF) << 24) | ((red & 0xFF) << 16) | ((green & 0xFF) << 8) | ((blue & 0xFF));
            }
        }else if(transferFunction.equalsIgnoreCase("Logarithmic")){
            for(int i = 0; i < nPixels; ++i){
                red = (int)(255.0f * overexposure * (Math.log(factor * redBuffer.get(i)/ maxRed + 1)) );
                green = (int)(255.0f * overexposure * (Math.log(factor * greenBuffer.get(i)/ maxGreen + 1)) );
                blue = (int)(255.0f * overexposure * (Math.log(factor * blueBuffer.get(i)/ maxBlue + 1)) );
                red = Math.min(Math.max(red, 0),255);
                green = Math.min(Math.max(green, 0),255);
                blue = Math.min(Math.max(blue, 0),255);
                if(switchColorChannels){
                    int temp = red;
                    red = green;
                    green = blue;
                    blue = temp;
                }
                argb[i] = ((0xFF) << 24) | ((red & 0xFF) << 16) | ((green & 0xFF) << 8) | ((blue & 0xFF));
            }
        }else{
            for(int i = 0; i < nPixels; ++i){
                red = (int)(255.0f * overexposure * redBuffer.get(i) / maxRed);
                green = (int)(255.0f * overexposure * greenBuffer.get(i) / maxGreen);
                blue = (int)(255.0f * overexposure * blueBuffer.get(i) / maxBlue);
                red = Math.min(Math.max(red, 0),255);
                green = Math.min(Math.max(green, 0),255);
                blue = Math.min(Math.max(blue, 0),255);
                if(switchColorChannels){
                    int temp = red;
                    red = green;
                    green = blue;
                    blue = temp;
                }
                argb[i] = ((0xFF) << 24) | ((red & 0xFF) << 16) | ((green & 0xFF) << 8) | ((blue & 0xFF));
            }
        }
    }

    public void saveImage(String text) throws IOException {
        System.out.println("Saving image " + text + " -> " + new Date());
        BufferedImage bi = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_ARGB);
        bi.setRGB(0, 0, imageWidth, imageHeight, argb, 0, imageWidth);
        ImageIO.write(bi, "png", new File(text));
    }

    public void setBlueMax(int blueMax) {
        this.blueMax = blueMax;
    }

    public void setBlueMin(int blueMin) {
        this.blueMin = blueMin;
    }

    public void setExponent(float exponent) {
        this.exponent = exponent;
    }

    public void setFactor(float factor) {
        this.factor = factor;
    }

    public void setGreenMax(int greenMax) {
        this.greenMax = greenMax;
    }

    public void setGreenMin(int greenMin) {
        this.greenMin = greenMin;
    }

    public void setImageHeight(int imageHeight) {
        this.imageHeight = imageHeight;
    }

    public void setImageWidth(int imageWidth) {
        this.imageWidth = imageWidth;
    }

    public void setOverexposure(float overexposure) {
        this.overexposure = overexposure;
    }

    public void setRedMax(int redMax) {
        this.redMax = redMax;
    }

    public void setRedMin(int redMin) {
        this.redMin = redMin;
    }

    public void setSamplesPerWorkingItemSide(int samplesPerWorkingItemSide) {
        this.samplesPerWorkingItemSide = samplesPerWorkingItemSide;
    }

    public void setTransferFunction(String transferFunction) {
        this.transferFunction = transferFunction;
    }

    public void setSwitchColorChannels(boolean switchColorChannels) {
        this.switchColorChannels = switchColorChannels;
    }
    
    
}
