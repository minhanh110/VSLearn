import type React from "react"
import { Button } from "@/components/ui/button"
import { Input } from "@/components/ui/input"
import Link from "next/link"
import { BackgroundLayout } from "../../components/background-layout"
import { WhaleCharacter } from "../../components/whale-character"
import { BackgroundCard } from "../../components/background-card"

export default function OTPPage() {
  return (
    <BackgroundLayout>
      <BackgroundCard>
        <div className="grid lg:grid-cols-2 min-h-[500px]">
          {/* Left side - OTP Form */}
          <div className="p-8 lg:p-12 flex flex-col justify-center">
            <div className="max-w-sm mx-auto w-full space-y-6">
              {/* Title */}
              <div className="text-center space-y-2">
                <h1 className="text-3xl font-bold text-gray-900">Verify Your Email</h1>
                <p className="text-gray-600">
                  We've sent a 6-digit code to{" "}
                  <span className="font-medium" style={{ color: "#93D6F6" }}>
                    john@example.com
                  </span>
                </p>
              </div>

              {/* OTP Form */}
              <form className="space-y-6">
                <div className="space-y-4">
                  <div className="flex justify-center space-x-3">
                    {[1, 2, 3, 4, 5, 6].map((index) => (
                      <Input
                        key={index}
                        type="text"
                        maxLength={1}
                        className="w-12 h-12 text-center text-xl font-bold border-2 border-gray-200 rounded-lg focus:ring-2 focus:border-transparent"
                        style={{ "--tw-ring-color": "#93D6F6" } as React.CSSProperties}
                      />
                    ))}
                  </div>
                </div>

                <Button
                  type="submit"
                  className="w-full text-white py-3 rounded-lg font-medium transition-colors hover:opacity-90"
                  style={{ backgroundColor: "#93D6F6" }}
                >
                  Verify Code
                </Button>
              </form>

              {/* Resend */}
              <div className="text-center space-y-2">
                <p className="text-sm text-gray-600">Didn't receive the code?</p>
                <Button variant="ghost" className="hover:opacity-80" style={{ color: "#93D6F6" }}>
                  Resend Code
                </Button>
              </div>

              {/* Back Link */}
              <div className="text-center">
                <Link href="/login" className="text-sm text-gray-500 hover:text-gray-700">
                  ← Back to Login
                </Link>
              </div>
            </div>
          </div>

          {/* Right side - Illustration */}
          <div className="flex items-center justify-center relative overflow-hidden">
            <WhaleCharacter expression="winking" message="Check your email!" />
          </div>
        </div>
      </BackgroundCard>
    </BackgroundLayout>
  )
}
