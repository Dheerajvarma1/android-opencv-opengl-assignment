#version 300 es
precision mediump float;

// Vertex attributes
in vec4 aPosition;
in vec2 aTexCoord;

// Output to fragment shader
out vec2 vTexCoord;

void main() {
    vTexCoord = aTexCoord;
    gl_Position = aPosition;
}

