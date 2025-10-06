"use client"

interface ImageProcessingResult {
  id: number
  originalImage: string
  processedImage: string
  lower: number
  upper: number
  blur: number
  fps: string
}

export function EdgeDetectionShowcase() {
  const results: ImageProcessingResult[] = [
    {
      id: 1,
      originalImage: "/WhatsApp Image 2025-10-06 at 05.50.58_5ded6a26.jpg",
      processedImage: "/WhatsApp Image 2025-10-06 at 05.51.54_9f326b7a.jpg",
      lower: 50,
      upper: 150,
      blur: 5,
      fps: "40-55",
    },
    {
      id: 2,
      originalImage: "/WhatsApp Image 2025-10-06 at 05.59.40_25d60535.jpg",
      processedImage: "/WhatsApp Image 2025-10-06 at 05.59.40_a3883ccb.jpg",
      lower: 31,
      upper: 40,
      blur: 2,
      fps: "40-55",
    },
    {
      id: 3,
      originalImage: "/WhatsApp Image 2025-10-06 at 06.05.48_06536a16.jpg",
      processedImage: "/WhatsApp Image 2025-10-06 at 06.05.48_25529161.jpg",
      lower: 40,
      upper: 120,
      blur: 7,
      fps: "40-55",
    },
    {
      id: 4,
      originalImage: "/WhatsApp Image 2025-10-06 at 06.09.36_6b1ad8da.jpg",
      processedImage: "/WhatsApp Image 2025-10-06 at 06.09.36_c3f572ae.jpg",
      lower: 70,
      upper: 200,
      blur: 5,
      fps: "40-55",
    },
  ]

  return (
    <div className="space-y-16">
      <div className="text-center space-y-4">
        <h2 className="text-3xl font-bold font-mono text-foreground">Video Edge Detection Showcase</h2>
        <p className="text-muted-foreground max-w-2xl mx-auto leading-relaxed">
          Showcasing Canny edge detection algorithm applied to various images with optimized parameters.
          Each result displays the original image alongside its processed counterpart with detailed processing
          parameters.
        </p>
      </div>

      <div className="grid gap-12">
        {results.map((result, index) => (
          <ImageProcessingCard key={result.id} result={result} index={index} />
        ))}
      </div>
    </div>
  )
}

function ImageProcessingCard({
  result,
  index,
}: {
  result: ImageProcessingResult
  index: number
}) {
  return (
    <div className="border border-border rounded-lg p-6 bg-card hover:border-primary/50 transition-all duration-300">
      <div className="flex items-center justify-between mb-6">
        <div className="flex items-center gap-3">
          <div className="w-2 h-2 rounded-full bg-primary" />
          <h3 className="text-xl font-mono text-primary">VIDEO #{String(index + 1).padStart(2, "0")}</h3>
        </div>
      </div>

      <div className="grid md:grid-cols-2 gap-6">
        {/* Original Image */}
        <div className="space-y-3">
          <div className="flex items-center justify-between">
            <span className="text-xs font-mono text-muted-foreground uppercase tracking-wider">Original Video</span>
          </div>
          <div className="aspect-video bg-secondary rounded border border-border overflow-hidden">
            <img
              src={result.originalImage}
              alt={`Original image ${index + 1}`}
              className="w-full h-full object-cover"
            />
          </div>
        </div>

        {/* Processed Image */}
        <div className="space-y-3">
          <div className="flex items-center justify-between">
            <span className="text-xs font-mono text-primary uppercase tracking-wider">Edge Detected</span>
          </div>
          <div className="aspect-video bg-secondary rounded border border-primary/30 overflow-hidden">
            <img
              src={result.processedImage}
              alt={`Edge detected image ${index + 1}`}
              className="w-full h-full object-cover"
            />
          </div>
        </div>
      </div>

      {/* Parameters */}
      <div className="mt-6 pt-6 border-t border-border">
        <div className="grid grid-cols-2 md:grid-cols-4 gap-4">
          <div className="space-y-1">
            <p className="text-xs font-mono text-muted-foreground uppercase">FPS</p>
            <p className="text-lg font-mono text-primary">{result.fps}</p>
          </div>
          <div className="space-y-1">
            <p className="text-xs font-mono text-muted-foreground uppercase">Lower</p>
            <p className="text-lg font-mono text-foreground">{result.lower}</p>
          </div>
          <div className="space-y-1">
            <p className="text-xs font-mono text-muted-foreground uppercase">Upper</p>
            <p className="text-lg font-mono text-foreground">{result.upper}</p>
          </div>
          <div className="space-y-1">
            <p className="text-xs font-mono text-muted-foreground uppercase">Blur</p>
            <p className="text-lg font-mono text-foreground">{result.blur}</p>
          </div>
        </div>
      </div>
    </div>
  )
}
