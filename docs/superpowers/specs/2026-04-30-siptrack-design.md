# SipTrack - Design Spec

## Overview
Premium alcohol tracking and lifestyle analytics Android app (Strava for drinks). Built with Kotlin, Jetpack Compose, Supabase backend, OpenRouter AI insights. No subscription tiers - fully free.

## Architecture
- **Pattern**: MVVM with Repository per feature
- **Structure**: Single module, feature-based packages
- **DI**: Hilt
- **State**: Compose state flows (StateFlow/ViewModel)

## Project Structure
```
app/src/main/java/com/siptrack/
├── core/
│   ├── ui/          # Reusable UI (Cards, Buttons, Theme)
│   ├── utils/       # Extensions, Constants
│   └── di/          # Hilt modules
├── auth/            # Login/Onboarding
├── drinks/          # Drink logging
├── dashboard/       # Main stats screen
├── map/             # Google Maps view
├── insights/        # Analytics + AI
├── profile/         # User profile + achievements
└── SipTrackApp.kt   # Application class, nav graph
```

## Data Models
```kotlin
enum class DrinkType { BEER, WHISKY, VODKA, WINE, COCKTAIL }
enum class MoodTag { HAPPY, PARTY, STRESS, CHILL, OTHER }

data class Profile(val id: String, val displayName: String?, val avatarUrl: String?, val createdAt: String?)
data class Drink(val id: String, val brand: String, val type: DrinkType, val alcoholPercentage: Float, val volumeMl: Float, val pricePerServing: Float?, val isCustom: Boolean, val createdBy: String?, val createdAt: String?)
data class DrinkLog(val id: String, val userId: String, val drinkId: String, val quantityMl: Float, val quantityServings: Int, val mood: MoodTag?, val notes: String?, val locationLat: Double?, val locationLng: Double?, val locationName: String?, val consumedAt: String, val createdAt: String?)
data class Achievement(val id: String, val name: String, val description: String, val iconUrl: String?, val criteria: Map<String, Any>?)
data class UserAchievement(val id: String, val userId: String, val achievementId: String, val unlockedAt: String)
data class Insight(val id: String, val userId: String, val content: String, val generatedAt: String)
```

## Supabase Schema
Tables: `profiles`, `drinks`, `drink_logs`, `achievements`, `user_achievements`, `insights`
- RLS enabled on all user-data tables
- Users can only access their own data
- Drinks table is public read (master list)

## UI/UX
- **Theme**: Dark (#0F0F14 bg, #1A1A24 surface, #FF6B35 primary)
- **Style**: Glassmorphism cards, neumorphic FAB, clean typography (Inter)
- **Screens**: Onboarding (3 slides, real drink images), Auth (email + Google), Dashboard (tabs, stats, charts), Log Drink (search, sliders, mood tags), Map (markers, heatmap, timeline), Insights (AI cards), Profile (achievements grid)
- **Navigation**: Bottom nav (Home/Map/Insights/Profile), auth flow separate

## Feature Workflows
- **Drink Logging**: FAB → Log screen → Save → Supabase insert → Dashboard refresh
- **Map**: Date range → Fetch location logs → Render markers + heatmap → Timeline slider
- **Insights**: Trigger → Fetch 30-day logs → OpenRouter API → Cache to Supabase → Display
- **Auth**: Launch → Check session → Authenticated? Dashboard : Auth → Create profile

## AI Integration (OpenRouter)
- **Endpoint**: `https://openrouter.ai/api/v1/chat/completions`
- **Model**: `meta-llama/llama-3.1-8b-instruct:free`
- **Prompt**: Generate 3 non-medical insights from last 30 days of logs (JSON array output)
- **Fallback**: Show cached insight or "Insights unavailable"

## Key Dependencies
- Jetpack Compose, Material 3
- Supabase (Auth, PostgREST)
- Hilt
- Google Maps Compose
- MPAndroidChart
- OpenRouter (Retrofit)
- Coil (image loading)

## Scope Notes
- No subscription/premium tier logic (fully free)
- Private map only (no public aggregates)
- Full OpenRouter integration (no mock insights)
- Email + Google login via Supabase Auth
