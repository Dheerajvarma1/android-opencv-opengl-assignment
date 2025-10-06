/**
 * Android OpenCV Web Viewer - Main TypeScript Module
 * Handles image loading, DOM manipulation, and statistics display
 */

interface FrameStats {
    fps: number;
    resolution: string;
    processingTime: number;
    frameCount: number;
}

interface ImageData {
    src: string;
    width: number;
    height: number;
    loaded: boolean;
}

class OpenCVWebViewer {
    private frameImage!: HTMLImageElement;
    private frameCanvas!: HTMLCanvasElement;
    private canvasContext!: CanvasRenderingContext2D;
    private toggleButton!: HTMLButtonElement;
    private loadButton!: HTMLButtonElement;
    private overlayText!: HTMLDivElement;
    private themeToggle!: HTMLButtonElement;
    private themeIcon!: HTMLSpanElement;
    
    // Statistics elements
    private fpsElement!: HTMLSpanElement;
    private resolutionElement!: HTMLSpanElement;
    private processingTimeElement!: HTMLSpanElement;
    private frameCountElement!: HTMLSpanElement;
    
    private currentStats!: FrameStats;
    private isCanvasMode: boolean = false;
    private frameCounter: number = 0;
    private lastUpdateTime: number = 0;
    private isDarkTheme: boolean = false;

    constructor() {
        this.initializeElements();
        this.setupEventListeners();
        this.initializeStats();
        this.loadSampleImage();
    }

    private initializeElements(): void {
        // Get DOM elements
        this.frameImage = document.getElementById('frameImage') as HTMLImageElement;
        this.frameCanvas = document.getElementById('frameCanvas') as HTMLCanvasElement;
        this.toggleButton = document.getElementById('toggleView') as HTMLButtonElement;
        this.loadButton = document.getElementById('loadImage') as HTMLButtonElement;
        this.overlayText = document.getElementById('overlayText') as HTMLDivElement;
        this.themeToggle = document.getElementById('themeToggle') as HTMLButtonElement;
        this.themeIcon = document.getElementById('themeIcon') as HTMLSpanElement;
        
        // Statistics elements
        this.fpsElement = document.getElementById('fpsValue') as HTMLSpanElement;
        this.resolutionElement = document.getElementById('resolutionValue') as HTMLSpanElement;
        this.processingTimeElement = document.getElementById('processingTimeValue') as HTMLSpanElement;
        this.frameCountElement = document.getElementById('frameCountValue') as HTMLSpanElement;
        
        // Get canvas context
        this.canvasContext = this.frameCanvas.getContext('2d')!;
    }

    private setupEventListeners(): void {
        this.toggleButton.addEventListener('click', () => this.toggleViewMode());
        this.loadButton.addEventListener('click', () => this.loadSampleImage());
        this.themeToggle.addEventListener('click', () => this.toggleTheme());
        
        // Image load event
        this.frameImage.addEventListener('load', () => this.onImageLoaded());
        this.frameImage.addEventListener('error', () => this.onImageError());
        
        // Initialize theme from localStorage
        this.initializeTheme();
        
        // Simulate frame updates for demo purposes
        this.startFrameSimulation();
    }

    private initializeStats(): void {
        this.currentStats = {
            fps: 0,
            resolution: '--',
            processingTime: 0,
            frameCount: 0
        };
        this.updateStatsDisplay();
    }

    private loadSampleImage(): void {
        try {
            // Try to load a sample image - fallback to a placeholder if not found
            const imagePath = './assets/sample-frame.jpg';
            this.frameImage.src = imagePath;
            this.frameImage.alt = 'Processed Frame from Android App';
            
            this.updateOverlayText('Loading sample frame...');
            this.loadButton.textContent = 'Loading...';
            this.loadButton.disabled = true;
            
        } catch (error) {
            console.error('Error loading image:', error);
            this.onImageError();
        }
    }

    private onImageLoaded(): void {
        const imageData: ImageData = {
            src: this.frameImage.src,
            width: this.frameImage.naturalWidth,
            height: this.frameImage.naturalHeight,
            loaded: true
        };

        // Update canvas size to match image
        this.frameCanvas.width = imageData.width;
        this.frameCanvas.height = imageData.height;
        
        // Draw image to canvas
        this.canvasContext.drawImage(this.frameImage, 0, 0);
        
        // Update statistics
        this.currentStats.resolution = `${imageData.width}x${imageData.height}`;
        this.updateStatsDisplay();
        
        this.updateOverlayText(`Loaded frame: ${imageData.width}x${imageData.height} pixels`);
        this.loadButton.textContent = 'Load Sample Frame';
        this.loadButton.disabled = false;
        
        console.log('Image loaded successfully:', imageData);
    }

    private onImageError(): void {
        this.updateOverlayText('Error loading image. Using placeholder data for demo.');
        this.loadButton.textContent = 'Retry Loading';
        this.loadButton.disabled = false;
        
        // Set placeholder stats for demo
        this.currentStats.resolution = '640x480';
        this.currentStats.fps = 30;
        this.currentStats.processingTime = 16.7;
        this.updateStatsDisplay();
        
        console.warn('Image failed to load, using demo data');
    }

    private toggleViewMode(): void {
        this.isCanvasMode = !this.isCanvasMode;
        
        if (this.isCanvasMode) {
            this.frameImage.style.display = 'none';
            this.frameCanvas.style.display = 'block';
            this.toggleButton.textContent = 'Switch to Image View';
            this.updateOverlayText('Canvas rendering mode active');
        } else {
            this.frameImage.style.display = 'block';
            this.frameCanvas.style.display = 'none';
            this.toggleButton.textContent = 'Switch to Canvas View';
            this.updateOverlayText('Image rendering mode active');
        }
    }

    private updateStatsDisplay(): void {
        this.fpsElement.textContent = this.currentStats.fps.toString();
        this.resolutionElement.textContent = this.currentStats.resolution;
        this.processingTimeElement.textContent = `${this.currentStats.processingTime.toFixed(1)}ms`;
        this.frameCountElement.textContent = this.currentStats.frameCount.toString();
        
        // Add visual feedback for updates
        this.addUpdateAnimation();
    }

    private addUpdateAnimation(): void {
        const elements = [this.fpsElement, this.resolutionElement, this.processingTimeElement, this.frameCountElement];
        elements.forEach(element => {
            element.classList.add('updated');
            setTimeout(() => element.classList.remove('updated'), 500);
        });
    }

    private updateOverlayText(text: string): void {
        this.overlayText.textContent = text;
    }

    private startFrameSimulation(): void {
        // Simulate frame updates for demo purposes
        setInterval(() => {
            this.simulateFrameUpdate();
        }, 1000 / 30); // 30 FPS simulation
    }

    private simulateFrameUpdate(): void {
        const now = performance.now();
        
        if (this.lastUpdateTime === 0) {
            this.lastUpdateTime = now;
            return;
        }
        
        const deltaTime = now - this.lastUpdateTime;
        this.lastUpdateTime = now;
        
        // Update frame counter
        this.frameCounter++;
        
        // Calculate FPS
        const fps = 1000 / deltaTime;
        this.currentStats.fps = Math.round(fps);
        
        // Simulate processing time
        this.currentStats.processingTime = Math.random() * 10 + 10; // 10-20ms
        
        // Update frame count
        this.currentStats.frameCount = this.frameCounter;
        
        // Update display every few frames to avoid too frequent updates
        if (this.frameCounter % 10 === 0) {
            this.updateStatsDisplay();
        }
        
        // Update canvas if in canvas mode
        if (this.isCanvasMode && this.frameImage.complete) {
            this.updateCanvasFrame();
        }
    }

    private updateCanvasFrame(): void {
        // Redraw the image with some visual effect to show it's updating
        this.canvasContext.clearRect(0, 0, this.frameCanvas.width, this.frameCanvas.height);
        this.canvasContext.drawImage(this.frameImage, 0, 0);
        
        // Add a subtle overlay to show activity
        const alpha = Math.sin(Date.now() * 0.005) * 0.1 + 0.1;
        this.canvasContext.fillStyle = `rgba(102, 126, 234, ${alpha})`;
        this.canvasContext.fillRect(0, 0, this.frameCanvas.width, this.frameCanvas.height);
    }

    // Public methods for external interaction
    public updateFrameStats(stats: Partial<FrameStats>): void {
        Object.assign(this.currentStats, stats);
        this.updateStatsDisplay();
    }

    public loadCustomImage(imageUrl: string): void {
        this.frameImage.src = imageUrl;
        this.updateOverlayText('Loading custom image...');
    }

    public getCurrentStats(): FrameStats {
        return { ...this.currentStats };
    }

    // Theme management methods
    private initializeTheme(): void {
        const savedTheme = localStorage.getItem('theme');
        if (savedTheme === 'dark') {
            this.isDarkTheme = true;
            document.documentElement.setAttribute('data-theme', 'dark');
            this.themeIcon.textContent = '☀️';
        } else {
            this.isDarkTheme = false;
            document.documentElement.setAttribute('data-theme', 'light');
            this.themeIcon.textContent = '🌙';
        }
    }

    private toggleTheme(): void {
        this.isDarkTheme = !this.isDarkTheme;
        
        if (this.isDarkTheme) {
            document.documentElement.setAttribute('data-theme', 'dark');
            this.themeIcon.textContent = '☀️';
            localStorage.setItem('theme', 'dark');
        } else {
            document.documentElement.setAttribute('data-theme', 'light');
            this.themeIcon.textContent = '🌙';
            localStorage.setItem('theme', 'light');
        }
        
        // Add a smooth transition effect
        document.body.style.transition = 'background 0.3s ease';
        setTimeout(() => {
            document.body.style.transition = '';
        }, 300);
    }
}

// Initialize the viewer when DOM is loaded
document.addEventListener('DOMContentLoaded', () => {
    console.log('Initializing Android OpenCV Web Viewer...');
    
    try {
        const viewer = new OpenCVWebViewer();
        
        // Make viewer globally available for debugging
        (window as any).openCVViewer = viewer;
        
        console.log('Web viewer initialized successfully');
    } catch (error) {
        console.error('Failed to initialize web viewer:', error);
        
        // Show error message to user
        const overlayText = document.getElementById('overlayText');
        if (overlayText) {
            overlayText.textContent = 'Error initializing viewer. Please check console for details.';
            overlayText.classList.add('error');
        }
    }
});

// Export for potential module usage
export { OpenCVWebViewer };
export type { FrameStats, ImageData };
