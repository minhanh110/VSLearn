import type React from "react"
import { Button } from "@/components/ui/button"
import { Input } from "@/components/ui/input"
import { Label } from "@/components/ui/label"
import { Avatar, AvatarFallback, AvatarImage } from "@/components/ui/avatar"
import { Textarea } from "@/components/ui/textarea"
import Link from "next/link"
import { BackgroundLayout } from "../../components/background-layout"
import { WhaleCharacter } from "../../components/whale-character"
import { Camera, User } from "lucide-react"
import { BackgroundCard } from "../../components/background-card"

export default function EditProfilePage() {
  return (
    <BackgroundLayout>
      <BackgroundCard>
        {/* Top section - Whale Character */}
        <div className="flex justify-center mb-8">
          <WhaleCharacter expression="happy" message="Looking good!" size="md" />
        </div>

        {/* Bottom section - Edit Profile Form */}
        <div className="p-8 lg:p-12">
          <div className="max-w-2xl mx-auto w-full space-y-6">
            {/* Title */}
            <div className="text-center space-y-2">
              <h1 className="text-3xl font-bold text-gray-900">Edit Profile</h1>
              <p className="text-gray-600">Update your personal information</p>
            </div>

            {/* Profile Picture */}
            <div className="flex flex-col items-center space-y-4">
              <div className="relative">
                <Avatar className="w-24 h-24">
                  <AvatarImage src="/placeholder.svg?height=96&width=96" alt="Profile" />
                  <AvatarFallback className="bg-blue-100 text-blue-600 text-2xl">
                    <User className="w-12 h-12" />
                  </AvatarFallback>
                </Avatar>
                <button
                  className="absolute -bottom-2 -right-2 text-white p-2 rounded-full shadow-lg transition-colors hover:opacity-90"
                  style={{ backgroundColor: "#93D6F6" }}
                >
                  <Camera className="w-4 h-4" />
                </button>
              </div>
              <p className="text-sm text-gray-500">Click to change profile picture</p>
            </div>

            {/* Edit Profile Form */}
            <form className="space-y-4">
              <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
                <div className="space-y-2">
                  <Label htmlFor="firstName" className="text-sm font-medium text-gray-700">
                    First Name
                  </Label>
                  <Input
                    id="firstName"
                    type="text"
                    defaultValue="John"
                    className="w-full px-4 py-3 border border-gray-200 rounded-lg focus:ring-2 focus:border-transparent"
                    style={{ "--tw-ring-color": "#93D6F6" } as React.CSSProperties}
                  />
                </div>
                <div className="space-y-2">
                  <Label htmlFor="lastName" className="text-sm font-medium text-gray-700">
                    Last Name
                  </Label>
                  <Input
                    id="lastName"
                    type="text"
                    defaultValue="Doe"
                    className="w-full px-4 py-3 border border-gray-200 rounded-lg focus:ring-2 focus:border-transparent"
                    style={{ "--tw-ring-color": "#93D6F6" } as React.CSSProperties}
                  />
                </div>
              </div>

              <div className="space-y-2">
                <Label htmlFor="email" className="text-sm font-medium text-gray-700">
                  Email
                </Label>
                <Input
                  id="email"
                  type="email"
                  defaultValue="john@example.com"
                  className="w-full px-4 py-3 border border-gray-200 rounded-lg focus:ring-2 focus:border-transparent"
                  style={{ "--tw-ring-color": "#93D6F6" } as React.CSSProperties}
                />
              </div>

              <div className="space-y-2">
                <Label htmlFor="phone" className="text-sm font-medium text-gray-700">
                  Phone Number
                </Label>
                <Input
                  id="phone"
                  type="tel"
                  defaultValue="+1 (555) 123-4567"
                  className="w-full px-4 py-3 border border-gray-200 rounded-lg focus:ring-2 focus:border-transparent"
                  style={{ "--tw-ring-color": "#93D6F6" } as React.CSSProperties}
                />
              </div>

              <div className="space-y-2">
                <Label htmlFor="bio" className="text-sm font-medium text-gray-700">
                  Bio
                </Label>
                <Textarea
                  id="bio"
                  placeholder="Tell us about yourself..."
                  className="w-full px-4 py-3 border border-gray-200 rounded-lg focus:ring-2 focus:border-transparent resize-none"
                  style={{ "--tw-ring-color": "#93D6F6" } as React.CSSProperties}
                  rows={3}
                />
              </div>

              <div className="flex flex-col sm:flex-row space-y-3 sm:space-y-0 sm:space-x-4">
                <Button
                  type="submit"
                  className="flex-1 text-white py-3 rounded-lg font-medium transition-colors hover:opacity-90"
                  style={{ backgroundColor: "#93D6F6" }}
                >
                  Save Changes
                </Button>
                <Button type="button" variant="outline" className="flex-1 py-3 rounded-lg font-medium">
                  Cancel
                </Button>
              </div>
            </form>

            {/* Additional Options */}
            <div className="text-center space-y-2">
              <Link href="/change-password" className="text-sm block hover:opacity-80" style={{ color: "#93D6F6" }}>
                Change Password
              </Link>
              <Link href="/dashboard" className="text-sm text-gray-500 hover:text-gray-700 block">
                ← Back to Dashboard
              </Link>
            </div>
          </div>
        </div>
      </BackgroundCard>
    </BackgroundLayout>
  )
}
