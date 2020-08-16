#!/bin/bash

DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" >/dev/null 2>&1 && pwd )"


echo 'Enter project name (e.g. KaseChange, Okservable)'
read project_name_beautiful

echo 'Enter project name in lowercase (e.g. kasechange, okservable)'
read project_name

echo 'Enter project description'
read project_description

echo 'Replacing templates...'
find "$DIR" -type f -not -path './.git/*' -not -path './gradle/*' -not -path './.gradle/*' -not -path './gradlew' -not -path './gradlew.bat' -not -path './setup.sh' -exec sed -i "s/@PROJECT_NAME@/$project_name/g;s/@PROJECT_NAME_BEAUTIFUL@/$project_name_beautiful/g;s/@PROJECT_DESCRIPTION@/$project_description/g" {} \;

echo 'Removing the setup script...'
rm "$DIR/setup.sh"