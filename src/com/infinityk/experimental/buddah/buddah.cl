//#pragma OPENCL EXTENSION cl_khr_global_int32_base_atomics : enable

kernel void buddah(int samplesPerWorkingItemSide, 
     int imageWidth,int imageHeight,
     global int *red, global int *green, global int *blue,
     int redMin, int redMax, int greenMin, int greenMax, int blueMin, int blueMax, int maxIters){ 
    
    //x = -2..1; y = -1..1;
    float xp = -2.0f + 3.0f * get_global_id(0) / get_global_size(0);
    float yp = -1.0f + 2.0f * get_global_id(1) / get_global_size(1);
    float sxp = 3.0f / get_global_size(0) / samplesPerWorkingItemSide;
    float syp = 2.0f / get_global_size(1) / samplesPerWorkingItemSide;
	
    int ix, iy;
    for(iy = 0; iy < samplesPerWorkingItemSide; ++iy){
	for(ix = 0; ix < samplesPerWorkingItemSide; ++ix){
            float2 p = (float2)(xp + sxp * ix, yp + syp * iy);
            float4 n = p.xyxx * p.xyyx;

            float q = n.x - p.x/2.0f + 0.125f + n.y;
            //skip main cardioid
            if (q * (q + (p.x - 0.25f)) < n.y*0.25f) { continue; }
            //skip left sphere 
            if (n.x + 2.0f*p.x + n.y < -0.9375f) { continue; }
			
            int iterations = 0;
            float2 z = (0.0f);
            float e1=0.0f,e2=0.0f,e3=0.0f,e4=0.0f,e5=0.0f,e6=0.0f,e7=0.0f;
            for(iterations = 0; iterations < maxIters; iterations += 8){
		n = z.xyxx * z.xyyx;			
		z.y = (2.0f * n.z); 
		z.x = n.x - n.y;
		z += p;
		e1 = n.x + n.y;
                
                n = z.xyxx * z.xyyx;			
		z.y = (2.0f * n.z); 
		z.x = n.x - n.y;
		z += p;
		e2 = n.x + n.y;

                n = z.xyxx * z.xyyx;			
		z.y = (2.0f * n.z); 
		z.x = n.x - n.y;
		z += p;
		e3 = n.x + n.y;

                n = z.xyxx * z.xyyx;			
		z.y = (2.0f * n.z); 
		z.x = n.x - n.y;
		z += p;
		e4 = n.x + n.y;

                n = z.xyxx * z.xyyx;			
		z.y = (2.0f * n.z); 
		z.x = n.x - n.y;
		z += p;
		e5 = n.x + n.y;

                n = z.xyxx * z.xyyx;			
		z.y = (2.0f * n.z); 
		z.x = n.x - n.y;
		z += p;
		e6 = n.x + n.y;

                n = z.xyxx * z.xyyx;			
		z.y = (2.0f * n.z); 
		z.x = n.x - n.y;
		z += p;
		e7 = n.x + n.y;

                n = z.xyxx * z.xyyx;			
		z.y = (2.0f * n.z); 
		z.x = n.x - n.y;
		z += p;
		if (n.x + n.y > 4.0f) { break; }
            }

            if (e1 > 4.0f) { iterations -= 7;}
            else if (e2 > 4.0f) { iterations -= 6;}
            else if (e3 > 4.0f) { iterations -= 5;}
            else if (e4 > 4.0f) { iterations -= 4;}
            else if (e5 > 4.0f) { iterations -= 3;}
            else if (e6 > 4.0f) { iterations -= 2;}
            else if (e7 > 4.0f) { iterations -= 1;}
           
            if ( iterations == maxIters){
		continue;
            }
			
            global int* target;
            if (iterations >= redMin && iterations < redMax){
		target = red;
            }else if (iterations >= greenMin && iterations < greenMax){
		target = green;
            }else if (iterations >= blueMin && iterations < blueMax){
		target = blue;
            }else{
                continue;
            }
			
            z = (0.0f);
            int imageX, imageY;
            for( ;iterations > 8; iterations-=8){
		n = z.xyxx * z.xyyx;			
		z.y = (2.0f * n.z); 
		z.x = n.x - n.y;
		z += p;
		imageX = (z.x + 2.0f) * imageWidth / 3.0f;
		imageY = (z.y + 1.0f) * imageHeight / 2.0f;
		if (imageX >= 0 && imageX < imageWidth && imageY >= 0 && imageY < imageHeight){
                    target[imageX + imageY * imageWidth]++;
		}

                n = z.xyxx * z.xyyx;			
		z.y = (2.0f * n.z); 
		z.x = n.x - n.y;
		z += p;
		imageX = (z.x + 2.0f) * imageWidth / 3.0f;
		imageY = (z.y + 1.0f) * imageHeight / 2.0f;
		if (imageX >= 0 && imageX < imageWidth && imageY >= 0 && imageY < imageHeight){
                    target[imageX + imageY * imageWidth]++;
		}

                n = z.xyxx * z.xyyx;			
		z.y = (2.0f * n.z); 
		z.x = n.x - n.y;
		z += p;
		imageX = (z.x + 2.0f) * imageWidth / 3.0f;
		imageY = (z.y + 1.0f) * imageHeight / 2.0f;
		if (imageX >= 0 && imageX < imageWidth && imageY >= 0 && imageY < imageHeight){
                    target[imageX + imageY * imageWidth]++;
		}

                n = z.xyxx * z.xyyx;			
		z.y = (2.0f * n.z); 
		z.x = n.x - n.y;
		z += p;
		imageX = (z.x + 2.0f) * imageWidth / 3.0f;
		imageY = (z.y + 1.0f) * imageHeight / 2.0f;
		if (imageX >= 0 && imageX < imageWidth && imageY >= 0 && imageY < imageHeight){
                    target[imageX + imageY * imageWidth]++;
		}

                n = z.xyxx * z.xyyx;			
		z.y = (2.0f * n.z); 
		z.x = n.x - n.y;
		z += p;
		imageX = (z.x + 2.0f) * imageWidth / 3.0f;
		imageY = (z.y + 1.0f) * imageHeight / 2.0f;
		if (imageX >= 0 && imageX < imageWidth && imageY >= 0 && imageY < imageHeight){
                    target[imageX + imageY * imageWidth]++;
		}

                n = z.xyxx * z.xyyx;			
		z.y = (2.0f * n.z); 
		z.x = n.x - n.y;
		z += p;
		imageX = (z.x + 2.0f) * imageWidth / 3.0f;
		imageY = (z.y + 1.0f) * imageHeight / 2.0f;
		if (imageX >= 0 && imageX < imageWidth && imageY >= 0 && imageY < imageHeight){
                    target[imageX + imageY * imageWidth]++;
		}

                n = z.xyxx * z.xyyx;			
		z.y = (2.0f * n.z); 
		z.x = n.x - n.y;
		z += p;
		imageX = (z.x + 2.0f) * imageWidth / 3.0f;
		imageY = (z.y + 1.0f) * imageHeight / 2.0f;
		if (imageX >= 0 && imageX < imageWidth && imageY >= 0 && imageY < imageHeight){
                    target[imageX + imageY * imageWidth]++;
		}

                n = z.xyxx * z.xyyx;			
		z.y = (2.0f * n.z); 
		z.x = n.x - n.y;
		z += p;
		imageX = (z.x + 2.0f) * imageWidth / 3.0f;
		imageY = (z.y + 1.0f) * imageHeight / 2.0f;
		if (imageX >= 0 && imageX < imageWidth && imageY >= 0 && imageY < imageHeight){
                    target[imageX + imageY * imageWidth]++;
		}

                n = z.xyxx * z.xyyx;			
		z.y = (2.0f * n.z); 
		z.x = n.x - n.y;
		z += p;
		imageX = (z.x + 2.0f) * imageWidth / 3.0f;
		imageY = (z.y + 1.0f) * imageHeight / 2.0f;
		if (imageX >= 0 && imageX < imageWidth && imageY >= 0 && imageY < imageHeight){
                    target[imageX + imageY * imageWidth]++;
		}
            }
            for( ;iterations > 0; --iterations){
		n = z.xyxx * z.xyyx;			
		z.y = (2.0f * n.z); 
		z.x = n.x - n.y;
		z += p;
		imageX = (z.x + 2.0f) * imageWidth / 3.0f;
		imageY = (z.y + 1.0f) * imageHeight / 2.0f;
		if (imageX >= 0 && imageX < imageWidth && imageY >= 0 && imageY < imageHeight){
                    //atom_inc(& target[imageX + imageY * imageWidth]);
                    target[imageX + imageY * imageWidth]++;
		}
            }
	}
    }
}