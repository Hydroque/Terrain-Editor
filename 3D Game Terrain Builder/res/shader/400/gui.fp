#version 400 core

in vec2 pass_texcoord;

uniform sampler2D u_texture0;

out vec4 out_Color;

void main(void) {
	
	out_Color = texture(u_texture0, pass_texcoord);
	
}
