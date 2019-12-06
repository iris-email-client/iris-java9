#!/bin/bash

echo "Alterando diretório: "
cd iris.produtos/target
pwd

java --module-path libs -m iris.ui.cli/br.unb.cic.iris.cli.MainProgram