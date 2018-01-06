precision mediump float;

varying vec3 f_Position;
varying vec4 f_VaryingColor;
varying vec3 f_MaskPosition;

uniform float u_ColorMixRate;
uniform float u_FinalAlpha;
uniform vec4 u_MixColor;

void main(){
	vec4 c=u_MixColor*(f_VaryingColor*u_ColorMixRate);
	c.a*=u_FinalAlpha;
	gl_FragColor=c;
}
