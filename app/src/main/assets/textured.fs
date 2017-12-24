precision mediump float;
varying vec2 vTextureCoord;

uniform sampler2D uTexture;

uniform sampler2D hTexture;

void main(){
	//vec4 c=texture2D(uTexture,vTextureCoord);
	gl_FragColor=texture2D(hTexture,vTextureCoord);
}
