"use client"

import type React from "react"

import { Button } from "@/components/ui/button"
import { Input } from "@/components/ui/input"
import { Label } from "@/components/ui/label"
import Link from "next/link"
import { BackgroundLayout } from "../../components/background-layout";
import { WhaleCharacter } from "../../components/whale-character";
import { BackgroundCard } from "../../components/background-card";

import { Shield, Eye, EyeOff, Check, X } from "lucide-react";
import { useState } from "react";

export default function ChangePasswordPage() {
  const [showPasswords, setShowPasswords] = useState({
    current: false,
    new: false,
    confirm: false,
  })

  const [newPassword, setNewPassword] = useState("")

  const togglePasswordVisibility = (field: keyof typeof showPasswords) => {
    setShowPasswords((prev) => ({
      ...prev,
      [field]: !prev[field],
    }))
  }

  // Password validation
  const passwordRequirements = [
    { text: "At least 8 characters", valid: newPassword.length >= 8 },
    { text: "One uppercase letter", valid: /[A-Z]/.test(newPassword) },
    { text: "One lowercase letter", valid: /[a-z]/.test(newPassword) },
    { text: "One number", valid: /\d/.test(newPassword) },
    { text: "One special character", valid: /[!@#$%^&*(),.?":{}|<>]/.test(newPassword) },
  ]

  return (
    <BackgroundLayout>
      <BackgroundCard>
        <div className="flex items-center justify-between">
          {/* Left side - Change Password Form */}
          <div className="w-full max-w-sm space-y-6">
            {/* Title */}
            <div className="text-center space-y-2">
              <div
                className="mx-auto w-16 h-16 rounded-full flex items-center justify-center mb-4"
                style={{ backgroundColor: "#93D6F6", opacity: 0.2 }}
              >
                <Shield className="w-8 h-8" style={{ color: "#93D6F6" }} />
              </div>
              <h1 className="text-3xl font-bold text-gray-900">Change Password</h1>
              <p className="text-gray-600">Keep your account secure</p>
            </div>

            {/* Change Password Form */}
            <form className="space-y-6">
              <div className="space-y-2">
                <Label htmlFor="currentPassword" className="text-sm font-medium text-gray-700">
                  Current Password
                </Label>
                <div className="relative">
                  <Input
                    id="currentPassword"
                    type={showPasswords.current ? "text" : "password"}
                    placeholder="Enter current password"
                    className="w-full px-4 py-3 pr-12 border border-gray-200 rounded-lg focus:ring-2 focus:border-transparent"
                    style={{ "--tw-ring-color": "#93D6F6" } as React.CSSProperties}
                  />
                  <button
                    type="button"
                    onClick={() => togglePasswordVisibility("current")}
                    className="absolute right-3 top-1/2 transform -translate-y-1/2 text-gray-400 hover:text-gray-600"
                  >
                    {showPasswords.current ? <EyeOff className="w-5 h-5" /> : <Eye className="w-5 h-5" />}
                  </button>
                </div>
              </div>

              <div className="space-y-2">
                <Label htmlFor="newPassword" className="text-sm font-medium text-gray-700">
                  New Password
                </Label>
                <div className="relative">
                  <Input
                    id="newPassword"
                    type={showPasswords.new ? "text" : "password"}
                    placeholder="Enter new password"
                    value={newPassword}
                    onChange={(e) => setNewPassword(e.target.value)}
                    className="w-full px-4 py-3 pr-12 border border-gray-200 rounded-lg focus:ring-2 focus:border-transparent"
                    style={{ "--tw-ring-color": "#93D6F6" } as React.CSSProperties}
                  />
                  <button
                    type="button"
                    onClick={() => togglePasswordVisibility("new")}
                    className="absolute right-3 top-1/2 transform -translate-y-1/2 text-gray-400 hover:text-gray-600"
                  >
                    {showPasswords.new ? <EyeOff className="w-5 h-5" /> : <Eye className="w-5 h-5" />}
                  </button>
                </div>
              </div>

              {/* Password Requirements - Real-time validation */}
              {newPassword && (
                <div className="bg-white/70 border border-gray-200 rounded-lg p-4">
                  <p className="text-sm font-medium text-gray-700 mb-3">Password Requirements:</p>
                  <div className="space-y-2">
                    {passwordRequirements.map((req, index) => (
                      <div key={index} className="flex items-center gap-2">
                        {req.valid ? (
                          <Check className="w-4 h-4 text-green-500" />
                        ) : (
                          <X className="w-4 h-4 text-red-400" />
                        )}
                        <span className={`text-sm ${req.valid ? "text-green-600 font-medium" : "text-gray-600"}`}>
                          {req.text}
                        </span>
                      </div>
                    ))}
                  </div>
                </div>
              )}

              <div className="space-y-2">
                <Label htmlFor="confirmPassword" className="text-sm font-medium text-gray-700">
                  Confirm New Password
                </Label>
                <div className="relative">
                  <Input
                    id="confirmPassword"
                    type={showPasswords.confirm ? "text" : "password"}
                    placeholder="Confirm new password"
                    className="w-full px-4 py-3 pr-12 border border-gray-200 rounded-lg focus:ring-2 focus:border-transparent"
                    style={{ "--tw-ring-color": "#93D6F6" } as React.CSSProperties}
                  />
                  <button
                    type="button"
                    onClick={() => togglePasswordVisibility("confirm")}
                    className="absolute right-3 top-1/2 transform -translate-y-1/2 text-gray-400 hover:text-gray-600"
                  >
                    {showPasswords.confirm ? <EyeOff className="w-5 h-5" /> : <Eye className="w-5 h-5" />}
                  </button>
                </div>
              </div>

              <Button
                type="submit"
                className="w-full text-white py-3 rounded-lg font-medium transition-colors hover:opacity-90"
                style={{ backgroundColor: "#93D6F6" }}
              >
                Update Password
              </Button>
            </form>

            {/* Back Link */}
            <div className="text-center">
              <Link href="/profile" className="text-sm text-gray-500 hover:text-gray-700">
                ← Back to Profile
              </Link>
            </div>
          </div>

          {/* Right side - Whale Character */}
          <div className="hidden lg:block flex-1 flex justify-center items-center">
            <div className="relative">
              <WhaleCharacter expression="happy" message="Stay secure!" size="md" />
            </div>
          </div>
        </div>
      </BackgroundCard>
    </BackgroundLayout>
  )
}
