
README

If you are reading this, it means you opened/extracted "mrunpacker.jar" using an archiver like
WinRAR, 7-Zip, Ark, etc. To run this program as any other, change your file association with the
"Open with" option on mrunpacker.jar, and select Java or OpenJDK. To have this option, you have
to install Java and add it as a program.

If you're on Windows, you just need to install Java 8+, you can download the installer here:
    https://adoptium.net/es/temurin/releases/?package=jre&os=windows&version=21
For that 1% of people still using 32-bit in 2024:
    https://adoptium.net/es/temurin/releases/?package=jre&os=windows&version=17

If the option doesn't show in the Open with option after installing Java, this program fixes it:
    https://johann.loefflmann.net/en/software/jarfix/index.html

----------------------------------------------------------------------------------------------------

On Linux, some distributions like Ubuntu and Arch make the option available if you install Java with
your package manager:
    #(Debian, Ubuntu, Linux Mint, Pop! OS):
    sudo apt update && sudo apt install openjdk-21-jre
    #(Arch Linux, EndeavourOS, Manjaro):
    sudo pacman -S jre21-openjdk
    #(Fedora, Nobara):
    sudo dnf install java-21-openjdk

Some others don't make the file association, like Fedora and Debian, so you have to create a .desktop
file:
    1. Open a text editor
    2. Paste this:
[Desktop Entry]
Name=OpenJDK Default Runtime
Comment=OpenJDK Runtime Environment
Keywords=java;runtime
Exec=java -jar
Terminal=false
Type=Application
MimeType=application/x-java-archive;application/java-archive;application/x-jar;
    3. Save the file as openjdk-jar.desktop
    4. Move it to ~/.local/share/applications (paste it in file manager path at the top)
