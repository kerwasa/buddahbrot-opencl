//#pragma OPENCL EXTENSION cl_khr_global_int32_base_atomics : enable




kernel void buddah(int samplesPerWorkingItemSide, 
     int imageWidth,int imageHeight,
     global int *red, global int *green, global int *blue,
     int redMin, int redMax, int greenMin, int greenMax, int blueMin, int blueMax, int maxIters,
     float ex){ 
    
    //x = -2..1; y = -1..1;
    float xp = -2.0f + 3.0f * get_global_id(0) / get_global_size(0);
    float yp = -1.0f + 2.0f * get_global_id(1) / get_global_size(1);
    float sxp = 3.0f / get_global_size(0) / samplesPerWorkingItemSide;
    float syp = 2.0f / get_global_size(1) / samplesPerWorkingItemSide;

    int ix, iy;
    for(iy = 0; iy < samplesPerWorkingItemSide; ++iy){
	for(ix = 0; ix < samplesPerWorkingItemSide; ++ix){
            float2 p = (float2)(xp + sxp * ix, yp + syp * iy);
         
            int iterations = 0;
            float2 z = (float2)(0.0f,0.0f);
            float l,temp1,temp2;
            float e1=0.0f,e2=0.0f,e3=0.0f,e4=0.0f,e5=0.0f,e6=0.0f,e7=0.0f;
            for(iterations = 0; iterations < maxIters; iterations+=8){
                l = length(z);
                temp1 = pow(l, ex);
                temp2 = ex * atan2(z.y,z.x);
		z = (float2)(temp1 * cos(temp2), temp1 * sin(temp2));
                z += p;
		e1 = l;

                l = length(z);
                temp1 = pow(l, ex);
                temp2 = ex * atan2(z.y,z.x);
		z = (float2)(temp1 * cos(temp2), temp1 * sin(temp2));
                z += p;
		e2 = l;

                l = length(z);
                temp1 = pow(l, ex);
                temp2 = ex * atan2(z.y,z.x);
		z = (float2)(temp1 * cos(temp2), temp1 * sin(temp2));
                z += p;
		e3 = l;
                
                l = length(z);
                temp1 = pow(l, ex);
                temp2 = ex * atan2(z.y,z.x);
		z = (float2)(temp1 * cos(temp2), temp1 * sin(temp2));
                z += p;
		e4 = l;

                l = length(z);
                temp1 = pow(l, ex);
                temp2 = ex * atan2(z.y,z.x);
		z = (float2)(temp1 * cos(temp2), temp1 * sin(temp2));
                z += p;
		e5 = l;

                l = length(z);
                temp1 = pow(l, ex);
                temp2 = ex * atan2(z.y,z.x);
		z = (float2)(temp1 * cos(temp2), temp1 * sin(temp2));
                z += p;
		e6 = l;

                l = length(z);
                temp1 = pow(l, ex);
                temp2 = ex * atan2(z.y,z.x);
		z = (float2)(temp1 * cos(temp2), temp1 * sin(temp2));
                z += p;
		e7 = l;

                l = length(z);
                temp1 = pow(l, ex);
                temp2 = ex * atan2(z.y,z.x);
		z = (float2)(temp1 * cos(temp2), temp1 * sin(temp2));
                z += p;
		if(l > 2.0f) { break; }
            }

            if (e1 > 2.0f) { iterations -= 7;}
            else if (e2 > 2.0f) { iterations -= 6;}
            else if (e3 > 2.0f) { iterations -= 5;}
            else if (e4 > 2.0f) { iterations -= 4;}
            else if (e5 > 2.0f) { iterations -= 3;}
            else if (e6 > 2.0f) { iterations -= 2;}
            else if (e7 > 2.0f) { iterations -= 1;}
           

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
			
            z = (float2)(0.0f,0.0f);
            int imageX,imageY;
            float imgPX, imgPY;
            imgPX = imageWidth / 3.0f;
            imgPY = imageHeight / 2.0f;
            for( ;iterations > 0; --iterations){
                l = length(z);
                temp1 = pow(l, ex);
                temp2 = ex * atan2(z.y,z.x);
		z = (float2)(temp1 * cos(temp2), temp1 * sin(temp2));
                z += p;
		imageX = (z.x + 2.0f) * imgPX;
		imageY = (z.y + 1.0f) * imgPY;
		if (imageX >= 0 && imageX < imageWidth && imageY >= 0 && imageY < imageHeight){
                    target[imageX + imageY * imageWidth]++;
		}
            }
	}
    }
}