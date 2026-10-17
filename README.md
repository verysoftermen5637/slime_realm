# Slime Realm (Minecraft 1.20.1 Fabric Mod)

**Slime Realm** is a complete, fully featured dimension mod for Minecraft 1.20.1 built on Fabric Loader and Fabric API.

## Features:
1. **Slime Realm Dimension**:
   - Custom dimension type & data-driven world generator.
   - Slime Plains biome with dense green fog, gelatinous slime water, and ambient slime particles.
   - 4x5 Slime Block portal activated by the mystical **Slime Core Igniter**.
   - Safe teleportation algorithm protecting players from void falls and suffocation.
2. **Slime Wood Set**:
   - Slime Log, Stripped Slime Log, Slime Planks, and Slime Sapling.
   - Bouncy **Slime Leaves**: cancels 100% of fall damage and imparts elastic trampoline bounce.
   - Decorative blocks: Slime Slab, Stairs, Fence, and Fence Gate.
3. **Slime Ores & Resources**:
   - Slime Ore (stone) & Deepslate Slime Ore generated in the Slime Realm.
   - Drops **Slime Gem** (Raw Slime Core), smelted into **Slime Ingot**.
4. **Tools & Slime Boots**:
   - Diamond-tier durability, faster than iron mining speed.
   - **Slime Sword**: Inflicts Slowness II and elastic recoil knockback.
   - **Slime Pickaxe**: 25% chance to duplicate slime balls when mining.
   - **Slime Boots**: 100% fall damage negation + Jump Boost II when sprinting!
5. **CI/CD Pipeline**:
   - GitHub Actions workflow (`.github/workflows/build.yml`) builds `./gradlew build` on JDK 17 Temurin and uploads `slime_realm-1.0.0.jar` to workflow artifacts!

## How to Build:
```bash
./gradlew build
```
The compiled jar will be at `build/libs/slime_realm-1.0.0.jar`.
