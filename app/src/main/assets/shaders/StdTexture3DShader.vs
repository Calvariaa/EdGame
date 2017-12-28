uniform mat4 u_MVPMatrix;
uniform mat4 u_MaskMatrix;

attribute vec3 a_Position;
attribute vec2 a_TexturePosition;
attribute vec4 a_Color;

varying vec3 f_Position;
varying vec2 f_TexturePosition;
varying vec4 f_Color;
varying vec3 f_MaskPosition;

void main(){
	f_Position=a_Position;
	f_MaskPosition=(u_MaskMatrix*vec4(a_Position,1.0)).xyz;
	f_TexturePosition=a_TexturePosition;
	f_Color=a_Color;
	gl_Position=u_MVPMatrix*vec4(a_Position,1.0);
}
