from reportlab.lib import colors
from reportlab.lib.pagesizes import A4
from reportlab.lib.utils import simpleSplit
from reportlab.pdfgen import canvas


OUT = "output/pdf/appunti_avl_4_casi.pdf"
W, H = A4
INK = colors.HexColor("#172438")
MUTED = colors.HexColor("#526175")
BLUE = colors.HexColor("#2055A5")
PALE = colors.HexColor("#F5F8FC")
LINE = colors.HexColor("#D8E1ED")

c = canvas.Canvas(OUT, pagesize=A4)
c.setTitle("Alberi AVL - proprietà, rotazioni e operazioni")
c.setAuthor("Appunti dalle slide 7 - Alberi AVL")


def label(x, y, value, size=10, color=INK, bold=False):
    c.setFillColor(color)
    c.setFont("Helvetica-Bold" if bold else "Helvetica", size)
    c.drawString(x, y, value)


def paragraph(x, y, value, width=W - 84, size=10, leading=15, color=INK, bold=False):
    font = "Helvetica-Bold" if bold else "Helvetica"
    for line in simpleSplit(value, font, size, width):
        label(x, y, line, size, color, bold)
        y -= leading
    return y


def footer(page):
    c.setStrokeColor(LINE)
    c.setLineWidth(1)
    c.line(42, 77, W - 42, 77)
    label(42, 60, "Fonte: slide 7 - Alberi AVL, Materiale Bonomo.", 8, MUTED)
    label(W - 65, 60, f"{page} / 2", 8, MUTED)


def node(x, y, value):
    c.setFillColor(colors.white)
    c.setStrokeColor(BLUE)
    c.setLineWidth(1.2)
    c.circle(x, y, 10, stroke=1, fill=1)
    c.setFont("Helvetica-Bold", 8)
    c.setFillColor(INK)
    c.drawCentredString(x, y - 3, str(value))


def tree(points, edges):
    c.setStrokeColor(MUTED)
    c.setLineWidth(1.2)
    for a, b in edges:
        x1, y1 = points[a]
        x2, y2 = points[b]
        c.line(x1, y1 - 9, x2, y2 + 9)
    for value, (x, y) in points.items():
        node(x, y, value)


def case(x, top, name, path, rotation, shape):
    width, height = 247, 180
    c.setFillColor(PALE)
    c.setStrokeColor(LINE)
    c.roundRect(x, top - height, width, height, 10, fill=1, stroke=1)
    label(x + 14, top - 25, name, 13, BLUE, True)
    label(x + 14, top - 46, path, 9.3)
    label(x + 14, top - 63, rotation, 9.1, MUTED)

    bx, ay = x + 66, top - 97
    if shape == "SS":
        before = {30: (bx + 18, ay), 20: (bx, ay - 25), 10: (bx - 18, ay - 50)}
        edges = [(30, 20), (20, 10)]
    elif shape == "DD":
        before = {10: (bx - 18, ay), 20: (bx, ay - 25), 30: (bx + 18, ay - 50)}
        edges = [(10, 20), (20, 30)]
    elif shape == "SD":
        before = {30: (bx + 18, ay), 10: (bx - 18, ay - 25), 20: (bx, ay - 50)}
        edges = [(30, 10), (10, 20)]
    else:
        before = {10: (bx - 18, ay), 30: (bx + 18, ay - 25), 20: (bx, ay - 50)}
        edges = [(10, 30), (30, 20)]
    tree(before, edges)
    label(x + 116, top - 132, "->", 12, BLUE, True)
    fx = x + 180
    after = {20: (fx, ay - 13), 10: (fx - 23, ay - 50), 30: (fx + 23, ay - 50)}
    tree(after, [(20, 10), (20, 30)])


label(42, 797, "ALBERI AVL", 23, INK, True)
label(42, 775, "1. Proprietà e quattro casi di rotazione", 11, MUTED)
c.setStrokeColor(BLUE)
c.setLineWidth(2)
c.line(42, 760, W - 42, 760)

label(42, 735, "L'idea in 3 righe", 13, BLUE, True)
label(42, 714, "Un AVL è un albero binario di ricerca (BST) bilanciato in altezza.", 10)
label(42, 696, "FB = altezza(sinistra) - altezza(destra): in un AVL ogni nodo ha FB -1, 0 o +1.", 10)
label(42, 678, "Dopo inserimento o cancellazione, un nodo con FB +2 o -2 va riequilibrato.", 10)

label(42, 643, "I 4 casi", 14, BLUE, True)
label(42, 624, "Negli esempi di inserimento: S = sinistra, D = destra.", 10, MUTED)

case(42, 604, "SS  -  sinistra-sinistra", "Esempio: inserisci 30, 20, 10", "Una rotazione a destra su 30", "SS")
case(306, 604, "DD  -  destra-destra", "Esempio: inserisci 10, 20, 30", "Una rotazione a sinistra su 10", "DD")
case(42, 409, "SD  -  sinistra-destra", "Esempio: inserisci 30, 10, 20", "Sinistra su 10, poi destra su 30", "SD")
case(306, 409, "DS  -  destra-sinistra", "Esempio: inserisci 10, 30, 20", "Destra su 30, poi sinistra su 10", "DS")

c.setStrokeColor(LINE)
c.setLineWidth(1)
c.line(42, 206, W - 42, 206)
label(42, 184, "Come scegliere la rotazione", 12, BLUE, True)
label(42, 164, "Se FB(v) = +2, guarda il figlio sinistro u:", 9.5)
label(306, 164, "Se FB(v) = -2, guarda il figlio destro u:", 9.5)
label(42, 145, "FB(u) >= 0: SS, ruota a destra su v", 9.2)
label(306, 145, "FB(u) <= 0: DD, ruota a sinistra su v", 9.2)
label(42, 127, "FB(u) < 0: SD, sinistra su u; destra su v", 9.2)
label(306, 127, "FB(u) > 0: DS, destra su u; sinistra su v", 9.2)
label(42, 101, "FB(u) = 0 può comparire dopo una cancellazione. Ogni rotazione conserva l'ordine BST.", 9.3, MUTED)
footer(1)
c.showPage()


label(42, 797, "OPERAZIONI E COSTI", 22, INK, True)
label(42, 775, "2. Come si mantiene il bilanciamento", 11, MUTED)
c.setStrokeColor(BLUE)
c.setLineWidth(2)
c.line(42, 760, W - 42, 760)

label(42, 733, "Inserimento", 13, BLUE, True)
y = paragraph(42, 712, "1. Inserisci il nuovo nodo come in un BST. Risali verso la radice e aggiorna le altezze: h(v) = 1 + max(h(sin), h(des)); l'albero vuoto ha altezza -1.")
y = paragraph(42, y - 4, "2. Sul primo nodo sbilanciato incontrato dal basso (FB = +2 o -2) applica la rotazione della pagina 1. Dopo l'inserimento basta una rotazione semplice o doppia.")

label(42, y - 13, "Cancellazione", 13, BLUE, True)
y = paragraph(42, y - 34, "1. Cancella come in un BST: foglia, nodo con un figlio o nodo con due figli (sostituito con predecessore o successore).")
y = paragraph(42, y - 4, "2. Dal padre del nodo fisicamente rimosso risali verso la radice. Aggiorna le altezze e ruota su ogni nodo che diventa sbilanciato: possono servire O(log n) rotazioni.")
y = paragraph(42, y - 4, "3. Anche quando il figlio alto ha FB = 0, usa la rotazione semplice indicata nella pagina 1.", size=9.5, color=MUTED)

label(42, y - 15, "Perché l'altezza è logaritmica", 13, BLUE, True)
y = paragraph(42, y - 36, "Fra gli AVL di altezza h, quello con meno nodi ha due sottoalberi di altezza h-1 e h-2. Quindi n(h) = 1 + n(h-1) + n(h-2), con n(0) = 1 e n(1) = 2.")
y = paragraph(42, y - 4, "La crescita è quella di Fibonacci: n(h) cresce esponenzialmente con h. Perciò, con n nodi, h = Theta(log n). Un AVL non può diventare una catena lunga n.")

label(42, y - 15, "Costi da ricordare", 13, BLUE, True)
y = paragraph(42, y - 36, "Ricerca: O(log n), come in un BST, perché segue un solo cammino. Inserimento e cancellazione: O(log n), includendo la risalita e le rotazioni. Ogni rotazione modifica pochi collegamenti: O(1).")

label(42, 358, "Esempio dopo una cancellazione: FB del figlio = 0", 13, BLUE, True)
label(42, 338, "Elimina 6: il nodo 4 ha FB +2, il figlio 2 ha FB 0. Basta ruotare a destra su 4.", 9.5)
label(122, 317, "prima", 9, MUTED)
label(411, 317, "dopo", 9, MUTED)
tree({4: (154, 293), 2: (122, 265), 6: (186, 265), 1: (102, 235), 3: (142, 235)}, [(4, 2), (4, 6), (2, 1), (2, 3)])
label(285, 260, "->", 13, BLUE, True)
tree({2: (430, 293), 1: (399, 265), 4: (461, 265), 3: (440, 235)}, [(2, 1), (2, 4), (4, 3)])

c.setFillColor(PALE)
c.setStrokeColor(LINE)
c.roundRect(42, 104, W - 84, 91, 9, fill=1, stroke=1)
label(55, 174, "Risposta breve da saper scrivere", 11, BLUE, True)
paragraph(55, 155, "Un AVL è un BST in cui le altezze dei due sottoalberi di ogni nodo differiscono al massimo di 1. Inserimento e cancellazione possono rompere questo vincolo: si aggiornano le altezze risalendo e si applicano rotazioni semplici (SS, DD) o doppie (SD, DS), senza cambiare l'ordine delle chiavi. L'altezza resta O(log n), quindi ricerca, inserimento e cancellazione costano O(log n).", W - 110, 9.2, 13)

footer(2)
c.showPage()
c.save()
