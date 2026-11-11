# Omni Sage — Android starter

A Kotlin + Jetpack Compose Android starter for the Omni Sage personal AI assistant.

## Current status
- Phase 1 foundation: app shell, dark theme, branding and new-chat action.
- Basic chat UI: message composer and message bubbles.
- The assistant reply is intentionally a preview placeholder. No AI provider is connected yet.

## Build an APK with GitHub Actions
1. Create a GitHub repository named `Omni-Sage`.
2. Upload the contents of this folder to the repository root.
3. Open **Actions** → **Build Omni Sage APK** → **Run workflow**.
4. When the run finishes, open its **Artifacts** section and download `omni-sage-debug-apk`.
5. Extract the downloaded artifact to get `app-debug.apk`.

## Important security note
Never put a private AI API key directly in the Android app or commit it to GitHub.
For a real AI connection, the next phase should add a small backend that stores the key securely.

## Package
`com.omnisage.app`

## Version
`0.1.0` — starter preview
