"""Verifica lo snapshot anche quando il progetto contiene già file .class."""

import json
import shutil
import subprocess
import sys
import tempfile
from pathlib import Path


root = Path(__file__).resolve().parent
with tempfile.TemporaryDirectory() as directory:
    project = Path(directory)
    shutil.copytree(root / "dataStructure", project / "dataStructure",
                    ignore=shutil.ignore_patterns("*.class"))
    for name in ("disegna_albero.py", "albero_viewer.html"):
        shutil.copy2(root / name, project / name)
    subprocess.run(["javac", "dataStructure/Main.java"], cwd=project, check=True)
    assert (project / "dataStructure/RedBlabkBST/RedBlackBST.class").exists()
    subprocess.run([sys.executable, "disegna_albero.py"], cwd=project, check=True)
    nodes = json.loads((project / "albero.json").read_text())["nodes"]
    assert nodes and nodes[0]["path"] == ""
    assert nodes[0]["size"] == len(nodes)
    assert all(type(node["isRed"]) is bool for node in nodes)
    assert "__TREE_DATA__" not in (project / "albero.html").read_text()

    (project / "dataStructure/Main.java").write_text('''package dataStructure;
public class Main {
    public static void main(String[] args) {
        System.out.println("\\t20\\t3\\t2\\tTXT\\tfalse");
        System.out.println("L\\t10\\t1\\t1\\tTXT\\ttrue");
        System.out.println("R\\t30\\t1\\t1\\tTXT");
    }
}
''')
    subprocess.run([sys.executable, "disegna_albero.py"], cwd=project, check=True)
    nodes = json.loads((project / "albero.json").read_text())["nodes"]
    assert nodes[0]["isRed"] is False
    assert nodes[1]["isRed"] is True
    assert "isRed" not in nodes[2]

print("Snapshot JSON e HTML verificati con classi preesistenti.")
