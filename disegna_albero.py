"""Esegue Main e crea uno snapshot JSON e una pagina HTML dell'albero."""

import json
import subprocess
import tempfile
from pathlib import Path


root = Path(__file__).resolve().parent
with tempfile.TemporaryDirectory() as classes:
    subprocess.run(
        ["javac", "-d", classes, "dataStructure/Main.java", "dataStructure/AVL/AVL.java"],
        cwd=root,
        check=True,
    )
    result = subprocess.run(
        ["java", "-cp", classes, "dataStructure.Main", "--tree"],
        cwd=root,
        check=True,
        capture_output=True,
        text=True,
    )

nodes = []
for line in result.stdout.splitlines():
    path, key, size, height, value = line.split("\t", 4)
    nodes.append({"path": path, "key": int(key), "value": value,
                  "size": int(size), "height": int(height)})
if not nodes or nodes[0]["path"] != "":
    raise SystemExit("Main non ha generato uno snapshot valido")

snapshot = {"nodes": nodes}
json_text = json.dumps(snapshot, ensure_ascii=False, indent=2)
(root / "albero.json").write_text(json_text + "\n")

template = (root / "albero_viewer.html").read_text()
embedded = json.dumps(snapshot, ensure_ascii=False).replace("<", "\\u003c")
(root / "albero.html").write_text(template.replace("__TREE_DATA__", embedded))
print(root / "albero.html")
