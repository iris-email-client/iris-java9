#!/bin/bash

echo "Alterando diretório: "
cd iris.produtos/target
pwd

java --module-path libs -m iris.ui.gui/br.unb.cic.iris.gui.MainProgram