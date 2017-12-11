precision mediump float;

varying vec3 f_Position;
varying vec2 f_TexturePosition;
varying vec4 f_Color;


uniform sampler2D u_Texture0;
uniform float u_ColorMixRate;

void main(){
	
	gl_FragColor=texture2D(kTexture,vTextureCoord);
}
