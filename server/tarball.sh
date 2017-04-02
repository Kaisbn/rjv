#!/bin/bash
#
# Generate a tarball with server, plugins and configurations.
# Usage: ./tarball.sh { OUTPUT_PATH }

set -o errexit
set -o nounset
set -o pipefail

SCRIPT_PATH=$(dirname $0)

EXPORT_PATH=${1:-$SCRIPT_PATH/../playbooks/roles/minecraft/files}
EXPORT_PATH=$(readlink -f $EXPORT_PATH)

cd "$SCRIPT_PATH/server"

rm -f "$EXPORT_PATH/minecraft.jar"
tar -hcvf "$EXPORT_PATH/minecraft.tar" \
    spigot-*.jar \
    plugins/*.jar \
    server.properties \
    eula.txt

echo "Output: $EXPORT_PATH/minecraft.tar"
