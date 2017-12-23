precision mediump float;
varying vec2 vTextureCoord;
uniform sampler2D kTexture;

void main(){
	gl_FragColor=texture2D(kTexture,vTextureCoord);
}
