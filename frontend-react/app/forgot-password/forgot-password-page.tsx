"use client"

import type React from "react"
import { Button } from "@/components/ui/button"
import { Input } from "@/components/ui/input"
import { Label } from "@/components/ui/label"
import Link from "next/link"
import { BackgroundLayout } from "../../components/background-layout"
import { WhaleCharacter } from "../../components/whale-character"
import { Mail, ArrowLeft } from "lucide-react"
import { useState } from "react"
import { BackgroundCard } from "../../components/background-card"

export default function ForgotPasswordPage() {
  const [email, setEmail] = useState("")
  const [showPasswordTips, setShowPasswordTips] = useState(false)

  const passwordTips = [
    "At least 8 characters",
    "One uppercase letter",
    "One lowercase letter",
    "One number",
    "One special character",
  ]

  return (
    <BackgroundLayout>
      <BackgroundCard>
        <div className="flex items-center justify-between">
          {/* Left side - Forgot Password Form */}
          <div className="w-full max-w-sm space-y-6">
            {/* Back Button */}
            <Link href="/login" className="inline-flex items-center text-sm text-gray-500 hover:text-gray-700">
              <ArrowLeft className="w-4 h-4 mr-2" />
              Back to Login
            </Link>

            {/* Title */}
            <div className="text-center space-y-2">
              <div
                className="mx-auto w-16 h-16 rounded-full flex items-center justify-center mb-4"
                style={{ backgroundColor: "#93D6F6", opacity: 0.2 }}
              >
                <Mail className="w-8 h-8" style={{ color: "#93D6F6" }} />
              </div>
              <h1 className="text-3xl font-bold text-gray-900">Forgot Password?</h1>
              <p className="text-gray-600">No worries, we'll send you reset instructions</p>
            </div>

            {/* Forgot Password Form */}
            <form className="space-y-4">
              <div className="space-y-2">
                <Label htmlFor="email" className="text-sm font-medium text-gray-700">
                  Email Address
                </Label>
                <Input
                  id="email"
                  type="email"
                  placeholder="Enter your email address"
                  value={email}
                  onChange={(e) => setEmail(e.target.value)}
                  onFocus={() => setShowPasswordTips(true)}
                  className="w-full px-4 py-3 border border-gray-200 rounded-lg focus:ring-2 focus:border-transparent"
                  style={{ "--tw-ring-color": "#93D6F6" } as React.CSSProperties}
                />
              </div>

              <Button
                type="submit"
                className="w-full text-white py-3 rounded-lg font-medium transition-colors hover:opacity-90"
                style={{ backgroundColor: "#93D6F6" }}
              >
                Send Reset Instructions
              </Button>
            </form>

            {/* Info Box */}
            <div className="p-4 rounded-lg border border-blue-200 bg-blue-50/50">
              <div className="flex items-start space-x-3">
                <Mail className="w-5 h-5 mt-0.5 flex-shrink-0" style={{ color: "#93D6F6" }} />
                <div className="text-sm" style={{ color: "#000000" }}>
                  <p className="font-medium mb-1">Check your email</p>
                  <p>We'll send a password reset link to your email address if an account exists.</p>
                </div>
              </div>
            </div>

            {/* Password Requirements Tip */}
            {showPasswordTips && (
              <div className="bg-white/70 border border-gray-200 rounded-lg p-4">
                <p className="text-sm font-medium text-gray-700 mb-3">When creating your new password, remember:</p>
                <div className="space-y-2">
                  {passwordTips.map((tip, index) => (
                    <div key={index} className="flex items-center gap-2">
                      <div className="w-4 h-4 rounded-full border-2 border-gray-300 flex items-center justify-center">
                        <div className="w-2 h-2 rounded-full bg-gray-300"></div>
                      </div>
                      <span className="text-sm text-gray-600">{tip}</span>
                    </div>
                  ))}
                </div>
              </div>
            )}

            {/* Alternative Options */}
            <div className="text-center space-y-2">
              <p className="text-sm text-gray-600">
                Remember your password?{" "}
                <Link href="/login" className="font-medium hover:opacity-80" style={{ color: "#93D6F6" }}>
                  Sign in
                </Link>
              </p>
              <p className="text-sm text-gray-600">
                Don't have an account?{" "}
                <Link href="/register" className="font-medium hover:opacity-80" style={{ color: "#93D6F6" }}>
                  Create one
                </Link>
              </p>
            </div>
          </div>

          {/* Right side - Whale Character */}
          <div className="hidden lg:block flex-1 flex justify-center items-center">
            <div className="relative">
              <WhaleCharacter expression="sleepy" message="Don't worry!" size="md" />
            </div>
          </div>
        </div>
      </BackgroundCard>
    </BackgroundLayout>
  )
}
