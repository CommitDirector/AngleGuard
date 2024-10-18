# AngleGuard (Warning: Do Not Use This Project!)

This repository contains the cleaned and deobfuscated source code
of [AngleGuard](https://builtbybit.com/resources/angleguard-anti-cw-anti-auto-totem.45856/), a severely flawed plugin.

> [!CAUTION]
> The obfuscated jar was obtained directly from the original author through Discord, not via unauthorized or illegal
> channels like BuiltByBit. This ensures compliance with all terms of service and intellectual property rights.
> The source code has been personally deobfuscated, cleaned, and formatted for better readability, as the original was
> highly obfuscated.

## Why Should You Avoid Using This Project?

AngleGuard is plagued with numerous issues that render it unreliable and insecure. Some key problems include:

1. **No Permission Checks:** Every player on your server can access critical commands like reloading the configuration
   or disabling checks. This is a massive security flaw.

2. **Poor Code Quality:** The code is so badly written that it's almost comical. The compiled source jar is
   approximately 134 KB, while the obfuscated version bloats to 2.3 MB—an almost 19x increase due to excessive
   obfuscation. This not only makes the code unnecessarily large but also worsens performance.

3. **Main Thread Overload:** Everything runs on the main thread, as the developers seem completely unaware of async
   programming. This causes significant performance degradation, especially on larger servers.

4. **Developer Response:** After raising these issues in the official Discord server, I was banned for simply asking
   questions. This shows a complete lack of willingness to address the plugin’s fundamental problems.

## What Should You Use Instead?

For a more reliable and effective anti-totem solution, I highly recommend
using [TotemGuard](https://github.com/Bram1903/TotemGuard), which is actively maintained and optimized for performance.

> **Note:** If there are any concerns regarding the content of this repository or if any organization believes their
> rights are being infringed, please contact me directly. I am more than willing to resolve any issues promptly and
> professionally.
