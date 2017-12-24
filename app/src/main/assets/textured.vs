uniform mat4 uMVPMatrix;
attribute vec3 aPosition;
attribute vec2 aTexCoord;
varying vec2 vTextureCoord;

void main(){
	gl_Position=uMVPMatrix*vec4(aPosition,1.0);
	vTextureCoord=aTexCoord;
}
