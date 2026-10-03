from reportlab.lib import colors
from reportlab.lib.enums import TA_CENTER, TA_LEFT
from reportlab.lib.pagesizes import A4
from reportlab.lib.styles import ParagraphStyle
from reportlab.lib.units import mm
from reportlab.platypus import (
    HRFlowable, KeepTogether, PageBreak, Paragraph, Preformatted,
    SimpleDocTemplate, Spacer, Table, TableStyle,
)


OUT = "output/pdf/appunti_abr_bst.pdf"
BLUE = colors.HexColor("#2055A5")
INK = colors.HexColor("#172438")
MUTED = colors.HexColor("#536277")
PALE = colors.HexColor("#F3F7FC")
LINE = colors.HexColor("#D8E1ED")

styles = {
    "title": ParagraphStyle("title", fontName="Helvetica-Bold", fontSize=20, leading=24, textColor=INK, spaceAfter=8),
    "subtitle": ParagraphStyle("subtitle", fontName="Helvetica", fontSize=10.5, leading=15, textColor=MUTED, spaceAfter=12),
    "h": ParagraphStyle("h", fontName="Helvetica-Bold", fontSize=12.5, leading=16, textColor=BLUE, spaceBefore=13, spaceAfter=6),
    "body": ParagraphStyle("body", fontName="Helvetica", fontSize=10, leading=14.5, textColor=INK, spaceAfter=6),
    "small": ParagraphStyle("small", fontName="Helvetica", fontSize=9, leading=12.5, textColor=INK, spaceAfter=4),
    "code": ParagraphStyle("code", fontName="Courier", fontSize=8.8, leading=12, textColor=INK, spaceAfter=0),
    "label": ParagraphStyle("label", fontName="Helvetica-Bold", fontSize=9, leading=12, textColor=BLUE, spaceAfter=0),
    "th": ParagraphStyle("th", fontName="Helvetica-Bold", fontSize=9, leading=12, textColor=colors.white),
    "td": ParagraphStyle("td", fontName="Helvetica", fontSize=9, leading=12, textColor=INK),
}


def P(text, style="body"):
    return Paragraph(text, styles[style])


def H(text):
    return P(text, "h")


def box(content, width=501, background=PALE):
    t = Table([[content]], colWidths=[width])
    t.setStyle(TableStyle([
        ("BACKGROUND", (0, 0), (-1, -1), background),
        ("BOX", (0, 0), (-1, -1), 0.7, LINE),
        ("LEFTPADDING", (0, 0), (-1, -1), 11),
        ("RIGHTPADDING", (0, 0), (-1, -1), 11),
        ("TOPPADDING", (0, 0), (-1, -1), 8),
        ("BOTTOMPADDING", (0, 0), (-1, -1), 7),
    ]))
    return t


def code(text):
    return box(Preformatted(text, styles["code"]))


def grid(rows, widths):
    cells = [[P(v, "th" if i == 0 else "td") for v in row] for i, row in enumerate(rows)]
    t = Table(cells, colWidths=widths, repeatRows=1, hAlign="LEFT")
    t.setStyle(TableStyle([
        ("BACKGROUND", (0, 0), (-1, 0), BLUE),
        ("ROWBACKGROUNDS", (0, 1), (-1, -1), [colors.white, PALE]),
        ("GRID", (0, 0), (-1, -1), 0.55, LINE),
        ("VALIGN", (0, 0), (-1, -1), "TOP"),
        ("LEFTPADDING", (0, 0), (-1, -1), 8),
        ("RIGHTPADDING", (0, 0), (-1, -1), 8),
        ("TOPPADDING", (0, 0), (-1, -1), 6),
        ("BOTTOMPADDING", (0, 0), (-1, -1), 6),
    ]))
    return t


story = []

# Page 1: definition and core operations.
story += [P("ABR / BST: le basi", "title"), P("Albero binario di ricerca - appunti dalle slide 6", "subtitle")]
story += [box(P("<b>Idea chiave.</b> Un ABR organizza un dizionario per chiave: a ogni confronto scegli solo uno dei due sottoalberi. Il tempo dipende dall'<b>altezza h</b> dell'albero.", "body"))]
story += [H("1. Definizione e proprietà"), P("Per <b>ogni nodo x</b>, tutte le chiavi del suo sottoalbero sinistro sono <b>&lt;= key(x)</b> e tutte quelle del sottoalbero destro sono <b>&gt; key(x)</b>. La regola riguarda <b>tutti i discendenti</b>, non soltanto i due figli."),
          P("Esempio valido: radice 8; a sinistra 4 con figli 3 e 6; a destra 12 con figli 10 e 14. La visita in ordine produce 3, 4, 6, 8, 10, 12, 14.", "small"),
          P("<b>Chiavi uguali:</b> la definizione delle slide le ammette a sinistra. Nel codice del dizionario, invece, <i>compareTo == 0</i> aggiorna il valore già associato alla chiave: quindi le chiavi sono uniche. Si sceglie una convenzione e la si usa sempre.", "small")]
story += [H("2. Rappresentazione"), P("Un nodo conserva <b>key</b>, eventuale <b>value</b>, puntatori <b>left</b> e <b>right</b>; l'albero conserva <b>root</b>. Le chiavi devono essere confrontabili (nelle slide Java: <i>Comparable</i>). Un campo <b>size</b> può memorizzare il numero di nodi del sottoalbero per rank e select."),
          H("3. Ricerca, inserimento, minimo e massimo"),
          code("cerca(x, k):\n  finche x != null:\n    se k == x.key: ritorna x.value\n    se k < x.key: x = x.left\n    altrimenti:   x = x.right\n  ritorna assente"),
          Spacer(1, 6),
          P("<b>Inserimento:</b> si seguono gli stessi confronti fino a un puntatore nullo; lì si crea una foglia. Nella versione dizionario, se la chiave è già presente si aggiorna il valore. Esempio: per cercare 10 nell'albero sopra si segue 8 → 12 → 10."),
          code("inserisci(x, k, v):\n  se x == null: ritorna nuovoNodo(k, v)\n  se k < x.key: x.left = inserisci(x.left, k, v)\n  altrimenti se k > x.key: x.right = inserisci(x.right, k, v)\n  altrimenti: x.value = v\n  aggiorna x.size, se presente; ritorna x\n# chiamata iniziale: root = inserisci(root, k, v)"),
          Spacer(1, 6),
          P("<b>Minimo:</b> dal nodo iniziale, scendi sempre a sinistra; <b>massimo:</b> sempre a destra. Se l'albero è vuoto, l'operazione non ha risultato. Ricerca, inserimento, min e max costano <b>O(h)</b>.")]

story += [PageBreak()]

# Page 2: traversals and deletion.
story += [P("Visite e cancellazione", "title"), P("Come ottenere le chiavi ordinate e rimuovere un nodo senza rompere l'ABR", "subtitle")]
story += [H("4. Visita in ordine"),
          P("Visita <b>sinistro → nodo → destro</b>: stampa le chiavi in ordine crescente. La visita inversa (<b>destro → nodo → sinistro</b>) le stampa in ordine decrescente. Entrambe visitano tutti gli n nodi: <b>O(n)</b>."),
          code("inOrdine(x):\n  se x == null: ritorna\n  inOrdine(x.left)\n  stampa x.key\n  inOrdine(x.right)"),
          H("5. Cancellazione: i tre casi"),
          grid([
              ["Nodo da cancellare", "Operazione", "Perché funziona"],
              ["Foglia", "Elimina il nodo.", "Non resta alcun sottoalbero da ricollocare."],
              ["Un solo figlio", "Collega il padre direttamente al figlio.", "Tutti i discendenti rimangono nel lato corretto."],
              ["Due figli", "Prendi il minimo del sottoalbero destro (successore), copia chiave e valore nel nodo, poi elimina quel minimo.", "Il successore è la più piccola chiave maggiore: rispetta entrambi i lati."],
          ], [112, 223, 166]),
          Spacer(1, 8),
          P("<b>Esempio:</b> eliminando 8 dall'albero della pagina 1, il successore è 10. La nuova radice diventa 10 e il vecchio nodo 10 viene rimosso dal sottoalbero destro. Il successore ha al massimo un figlio: non può avere un figlio sinistro, altrimenti quello sarebbe il minimo.", "small"),
          H("Pseudocodice essenziale"),
          code("elimina(x, k):\n  se x == null: ritorna null\n  se k < x.key: x.left = elimina(x.left, k)\n  altrimenti se k > x.key: x.right = elimina(x.right, k)\n  altrimenti:\n    se x.left == null: ritorna x.right\n    se x.right == null: ritorna x.left\n    s = minimo(x.right)\n    x.key, x.value = s.key, s.value\n    x.right = eliminaMinimo(x.right)\n  aggiorna x.size, se presente; ritorna x"),
          Spacer(1, 7),
          P("La ricerca del nodo, del successore e la rimozione seguono un cammino: <b>O(h)</b>. Ricorda di aggiornare anche la radice se cambia, e di copiare <b>chiave e valore</b> insieme.", "small")]

story += [PageBreak()]

# Page 3: complexity and ordered operations.
story += [P("Costi e interrogazioni ordinate", "title"), P("Cosa saper motivare e applicare negli esercizi", "subtitle")]
story += [H("6. Complessità: conta l'altezza"),
          grid([
              ["Operazione", "Costo", "Motivo"],
              ["Cerca, inserisci, cancella, min, max, predecessore", "O(h)", "Si percorre un cammino dalla radice o verso un estremo."],
              ["Visita completa in ordine", "O(n)", "Si visita ogni nodo una volta."],
              ["Stampa chiavi in [M, N]", "O(h + k)", "Si potano i rami inutili; k chiavi vengono stampate."],
          ], [216, 70, 215]),
          Spacer(1, 6),
          P("Se l'albero è <b>lineare</b>, h = O(n): operazioni di dizionario <b>O(n)</b> nel caso peggiore. Se è <b>bilanciato</b>, h = O(log n): <b>O(log n)</b>. Con inserimenti in ordine casuale, l'altezza attesa è O(log n); le slide riportano circa <b>1,39 log n</b> confronti medi per ricerca e inserimento. L'ordine casuale non dà una garanzia sul caso peggiore."),
          H("7. Predecessore e successore"),
          P("Il <b>predecessore</b> è la più grande chiave minore di quella del nodo. Se esiste il figlio sinistro, è il <b>massimo del sottoalbero sinistro</b>. Altrimenti risali tramite <i>parent</i> mentre il nodo corrente è un figlio sinistro; il primo padre raggiunto da destra è il predecessore. Se non esiste, il nodo era il minimo. Il <b>successore</b> è simmetrico: minimo a destra, altrimenti risali mentre sei figlio destro. Costo O(h)."),
          H("8. Query di intervallo, conteggi, rank e select"),
          P("Per stampare le chiavi fra <b>M e N</b>: visita il sinistro solo se key &gt; M; stampa il nodo se M &lt;= key &lt;= N; visita il destro solo se key &lt; N. Con chiavi uniche si evitano i rami che non possono contenere risposte."),
          P("Per contare le chiavi <b>maggiori di G</b> senza visitarne ognuna, conserva <b>size(x) = 1 + size(left) + size(right)</b>. Se key(x) &lt;= G, prosegui solo a destra. Se key(x) &gt; G, aggiungi <b>1 + size(right)</b> e prosegui a sinistra. Costo O(h); size va aggiornato dopo ogni modifica."),
          P("<b>rank(k)</b> conta le chiavi minori di k; <b>select(i)</b> trova la chiave di posizione i nell'ordine crescente. Con size dei sottoalberi bastano O(h). Senza size, il conteggio può richiedere la visita di molti nodi."),
          H("Risposta breve da saper scrivere"),
          box(P("Un ABR è un albero binario in cui, per ogni nodo, tutte le chiavi a sinistra sono minori (o minori o uguali, se si ammettono duplicati) e quelle a destra sono maggiori. Ricerca e inserimento seguono i confronti lungo un cammino. Per cancellare: elimino la foglia, collego l'unico figlio, oppure sostituisco il nodo con il successore e rimuovo quest'ultimo. Il costo è O(h): O(n) se l'albero degenera, O(log n) se è bilanciato. La visita in ordine restituisce le chiavi ordinate.", "small"))]


def footer(c, doc):
    c.saveState()
    w, _ = A4
    c.setStrokeColor(LINE)
    c.setLineWidth(0.7)
    c.line(42, 39, w - 42, 39)
    c.setFont("Helvetica", 7.5)
    c.setFillColor(MUTED)
    c.drawString(42, 27, "Fonte: slide 6 - ABR, Materiale Bonomo")
    c.drawRightString(w - 42, 27, f"{doc.page} / 3")
    c.restoreState()


doc = SimpleDocTemplate(
    OUT, pagesize=A4, rightMargin=47, leftMargin=47,
    topMargin=43, bottomMargin=52,
    title="ABR / BST - appunti per studiare e ripetere",
    author="Appunti dalle slide 6 - ABR",
)
doc.build(story, onFirstPage=footer, onLaterPages=footer)
