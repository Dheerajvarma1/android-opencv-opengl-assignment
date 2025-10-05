#version 300 es
precision mediump float;

// Input from vertex shader
in vec2 vTexCoord;

// Texture uniform
uniform sampler2D uTexture;

// Output color
out vec4 fragColor;

void main() {
    fragColor = texture(uTexture, vTexCoord);
}

