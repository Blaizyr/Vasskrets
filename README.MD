# Compose Multiplatform Application
# Vasskrets
> *"W systemie planetarnym występują złożone procesy cyrkulacji wody.  
> W systemie społecznym – złożone procesy cyrkulacji informacji."*

> *"In a planetary system, complex processes of water circulation occur.  
> In a social system, complex processes of information circulation take place."*

> *"I et planetsystem foregår det komplekse prosesser med vannets sirkulasjon.  
> I et sosialt system skjer det komplekse prosesser med informasjonsflyt."*

**Vasskrets** (norw. *cykl wodny*) to eksperymentalna aplikacja Compose Multiplatform, której celem jest stworzenie osobistego środowiska obiegu informacji.  
Notatki są tu jak krople deszczu – spadają, płyną, gromadzą się i odparowują.

**Vasskrets** (Norw. *The Water Cycle*) is an experimental Compose Multiplatform application aiming to create a personal environment for information circulation.  
Notes here are like raindrops — falling, flowing, gathering, and evaporating.

**Vasskrets** (norsk *vannets kretsløp*) er et eksperimentelt Compose Multiplatform-program som har som mål å skape et personlig miljø for informasjonsflyt.  
Notater her er som regndråper — de faller, flyter, samles og fordamper.

## Before running!
 - install JDK 17 or higher on your machine  
 - add `local.properties` file to the project root and set a path to Android SDK there  

### Android
To run the application on android device/emulator:  
 - open project in Android Studio and run imported android run configuration  

To build the application bundle:  
 - run `./gradlew :composeApp:assembleDebug`  
 - find `.apk` file in `composeApp/build/outputs/apk/debug/composeApp-debug.apk`  

Run android UI tests on the connected device: `./gradlew :composeApp:connectedDebugAndroidTest`

### Desktop
Run the desktop application: `./gradlew :composeApp:run`  
Run the desktop **hot reload** application: `./gradlew :composeApp:hotRunJvm`  
Run desktop UI tests: `./gradlew :composeApp:jvmTest`  

### Wasm Browser (Alpha)
Run the browser application: `./gradlew :composeApp:wasmJsBrowserDevelopmentRun --continue`  
Run browser UI tests: `./gradlew :composeApp:wasmJsBrowserTest`  

## License

This project is licensed under the MIT License.

**Terms:**

- Copying, modifying, merging, publishing, distributing, sublicensing, and/or selling copies of this software are activities that are not prohibited under this license.
- The software is provided "as is", without warranty of any kind, express or implied, including but not limited to the warranties of merchantability, fitness for a particular purpose, and noninfringement.
- In no event shall the authors or copyright holders be liable for any claim, damages, or other liability arising from the use or inability to use the software.
- Any use of this software is done at the user's sole risk.
- The above copyright notice and this permission notice must be included in all copies or substantial portions of the software.

**No support, maintenance, or guarantees are provided.**

Use at your own risk. 

