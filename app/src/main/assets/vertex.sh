uniform mat4 uMVPMatrix; //�ܱ任����
attribute vec3 aPosition;  //����λ��
attribute vec4 aColor;    //������ɫ
varying  vec4 vColor;  //���ڴ��ݸ�ƬԪ��ɫ��ı��
varying  vec4 aaPosition;

void main()     
{                            		
   gl_Position = uMVPMatrix * vec4(aPosition,1); //����ܱ任�������˴λ��ƴ˶���λ��
   aaPosition=vec4(aPosition,1);
   vColor =mix(aColor,vec4(0,0,0,0),0.0);//�����յ���ɫ���ݸ�ƬԪ��ɫ�� 
}                      
