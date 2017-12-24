precision mediump float;
varying  vec4 vColor; //���մӶ�����ɫ�����Ĳ���
varying vec4 aaPosition;

void main()                         
{                       
   /*vec4 gli=vec4(
   				vec3(
   					0.1*max(cos((abs(aaPosition.x)+abs(aaPosition.y))*10.0),-1.0)
   				),
   				0.0
   			);*/
   if(vColor.r<0.15){
   	gl_FragColor=vec4(1.0,1.0,1.0,1.0);
   }else{
   	gl_FragColor =vColor;//+gli;//���ƬԪ��ɫֵ
   }
}
