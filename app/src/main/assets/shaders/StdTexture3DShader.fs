precision mediump float;
varying vec3 f_Position;
varying vec2 f_TexturePosition;
varying vec4 f_Color;
uniform float u_ColorMixRate;
uniform float u_FinalAlpha;
uniform vec4 u_MixColor;
uniform sampler2D u_Texture;

void main(){
	vec4 c=u_MixColor*mix(texture2D(u_Texture,f_TexturePosition),f_Color,u_ColorMixRate);
	c.a=c.a*u_FinalAlpha;
	gl_FragColor=vec4(1.0,1.0,1.0,1.0)+c;
}
