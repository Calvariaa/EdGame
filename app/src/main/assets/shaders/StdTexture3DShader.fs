precision mediump float;

varying vec3 f_Position;
varying vec2 f_TexturePosition;
varying vec4 f_Color;


uniform sampler2D u_Texture0;
uniform float u_ColorMixRate;
uniform float u_FinalAlpha;
uniform vec4 u_MixColor;


void main(){
	vec4 c=u_MixColor*mix(texture2D(u_Texture0,f_TexturePosition),f_Color,u_ColorMixRate);
	c.a=c.a*u_FinalAlpha;
	gl_FragColor=c;
}
