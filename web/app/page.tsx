"use client"

import { useState, useEffect } from "react"
import { EdgeDetectionShowcase } from "@/components/edge-detection-showcase"

export default function Home() {
  const [theme, setTheme] = useState<"light" | "dark">("dark")

  useEffect(() => {
    // Check for saved theme preference or default to dark
    const savedTheme = localStorage.getItem("theme") as "light" | "dark" | null
    if (savedTheme) {
      setTheme(savedTheme)
      document.documentElement.classList.toggle("dark", savedTheme === "dark")
    } else {
      document.documentElement.classList.add("dark")
    }
  }, [])

  const toggleTheme = () => {
    const newTheme = theme === "dark" ? "light" : "dark"
    setTheme(newTheme)
    localStorage.setItem("theme", newTheme)
    document.documentElement.classList.toggle("dark", newTheme === "dark")
  }

  return (
    <main className="min-h-screen bg-background">
      {/* Header */}
      <header className="border-b border-border">
        <div className="container mx-auto px-6 py-8">
          <div className="flex items-start justify-between">
            <div>
              <h1 className="text-4xl font-bold font-mono text-primary mb-2">CANNY EDGE DETECTION</h1>
              <p className="text-muted-foreground font-mono text-sm">Video processing showcase</p>
            </div>
            <div className="flex items-center gap-4">
              {/* Theme Toggle */}
              <button
                onClick={toggleTheme}
                className="p-2 rounded border border-border bg-card hover:bg-accent transition-colors"
                aria-label="Toggle theme"
              >
                {theme === "dark" ? (
                  <svg
                    xmlns="http://www.w3.org/2000/svg"
                    width="20"
                    height="20"
                    viewBox="0 0 24 24"
                    fill="none"
                    stroke="currentColor"
                    strokeWidth="2"
                    strokeLinecap="round"
                    strokeLinejoin="round"
                    className="text-primary"
                  >
                    <circle cx="12" cy="12" r="4" />
                    <path d="M12 2v2" />
                    <path d="M12 20v2" />
                    <path d="m4.93 4.93 1.41 1.41" />
                    <path d="m17.66 17.66 1.41 1.41" />
                    <path d="M2 12h2" />
                    <path d="M20 12h2" />
                    <path d="m6.34 17.66-1.41 1.41" />
                    <path d="m19.07 4.93-1.41 1.41" />
                  </svg>
                ) : (
                  <svg
                    xmlns="http://www.w3.org/2000/svg"
                    width="20"
                    height="20"
                    viewBox="0 0 24 24"
                    fill="none"
                    stroke="currentColor"
                    strokeWidth="2"
                    strokeLinecap="round"
                    strokeLinejoin="round"
                    className="text-primary"
                  >
                    <path d="M12 3a6 6 0 0 0 9 9 9 9 0 1 1-9-9Z" />
                  </svg>
                )}
              </button>
              {/* Logo and FLAM Text */}
              <div className="flex items-center gap-3">
                <img
                  src="/flam-logo.png"
                  alt="Flam Logo"
                  className="h-12 w-12 object-contain"
                />
                <span className="text-2xl font-bold font-mono text-primary">FLAM</span>
              </div>
            </div>
          </div>
        </div>
      </header>

      {/* Content */}
      <div className="container mx-auto px-6 py-12">
        <EdgeDetectionShowcase />
      </div>

      {/* Footer */}
      <footer className="border-t border-border mt-24">
        <div className="container mx-auto px-6 py-8">
          <div className="text-center space-y-4">
            <p className="text-muted-foreground font-mono text-xs">
              Built with TypeScript + HTML • Canny Edge Detection Image Portfolio
            </p>
            <div className="space-y-2">
              <p className="text-sm font-mono text-primary">Subburi Dheeraj Varma</p>
              <div className="flex flex-wrap justify-center gap-4 text-xs font-mono text-muted-foreground">
                <span>📞 +91 7981692357</span>
                <a 
                  href="https://www.linkedin.com/in/dheeraj-varma-5061342b1/" 
                  target="_blank" 
                  rel="noopener noreferrer"
                  className="hover:text-primary transition-colors"
                >
                  💼 LinkedIn
                </a>
                <a 
                  href="https://github.com/Dheerajvarma1" 
                  target="_blank" 
                  rel="noopener noreferrer"
                  className="hover:text-primary transition-colors"
                >
                  🐙 GitHub
                </a>
                <a 
                  href="mailto:dheerajvarma031@gmail.com"
                  className="hover:text-primary transition-colors"
                >
                  📧 dheerajvarma031@gmail.com
                </a>
              </div>
            </div>
          </div>
        </div>
      </footer>
    </main>
  )
}
