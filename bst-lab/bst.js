// Port dei metodi di dataStructure/AVL/AVL.java, con nodi che contengono solo k.
class BST {
  root = null;
  balancePath = [];
  pendingRotation = null;

  size(x) { return x?.size ?? 0; }
  height(x) { return x?.height ?? 0; }
  update(x) {
    x.size = 1 + this.size(x.left) + this.size(x.right);
    x.height = 1 + Math.max(this.height(x.left), this.height(x.right));
    return x;
  }

  insert(k) {
    let step = this.beginInsert(k);
    while (step) step = this.continueRotation();
  }

  beginInsert(k) {
    if (this.pendingRotation) throw new Error('Completa la rotazione prima di modificare l’albero.');
    const path = [];
    let parent = null, side = null, x = this.root;
    while (x) {
      if (x.k === k) return null;
      path.push({ node: x, parent, side });
      parent = x;
      side = k < x.k ? 'left' : 'right';
      x = x[side];
    }
    const node = { k, left: null, right: null, size: 1, height: 1 };
    if (parent) parent[side] = node;
    else this.root = node;
    this.balancePath = path.reverse();
    return this.nextRotation();
  }

  rebalance(x) {
    this.update(x);
    const factor = this.balanceFactor(x);
    if (factor > 1) {
      if (this.balanceFactor(x.left) < 0) x.left = this.rotateLeft(x.left);
      return this.rotateRight(x);
    }
    if (factor < -1) {
      if (this.balanceFactor(x.right) > 0) x.right = this.rotateRight(x.right);
      return this.rotateLeft(x);
    }
    return x;
  }

  rotateRight(x) {
    const newRoot = x.left;
    x.left = newRoot.right;
    newRoot.right = x;
    this.update(x);
    return this.update(newRoot);
  }

  rotateLeft(x) {
    const newRoot = x.right;
    x.right = newRoot.left;
    newRoot.left = x;
    this.update(x);
    return this.update(newRoot);
  }

  get(x, k) {
    if (!x) return null;
    if (k < x.k) return this.get(x.left, k);
    if (k > x.k) return this.get(x.right, k);
    return x;
  }
  getSize(k) { return this.get(this.root, k)?.size ?? null; }
  getHeight(k) { return this.get(this.root, k)?.height ?? null; }
  getBalanceFactor(k) { return this.balanceFactor(this.get(this.root, k)); }
  balanceFactor(x) { return x ? this.height(x.left) - this.height(x.right) : 0; }

  InOrder() {
    const keys = [];
    const visit = x => { if (x) { visit(x.left); keys.push(x.k); visit(x.right); } };
    visit(this.root);
    return keys;
  }

  min(x) { return x.left ? this.min(x.left) : x; }
  deleteKey(k) {
    let step = this.beginDelete(k);
    while (step) step = this.continueRotation();
  }

  beginDelete(k) {
    if (this.pendingRotation) throw new Error('Completa la rotazione prima di modificare l’albero.');
    const path = [];
    let parent = null, side = null, x = this.root;
    while (x && x.k !== k) {
      path.push({ node: x, parent, side });
      parent = x;
      side = k < x.k ? 'left' : 'right';
      x = x[side];
    }
    if (!x) return null;

    if (x.left && x.right) {
      path.push({ node: x, parent, side });
      let successorParent = x, successorSide = 'right', successor = x.right;
      while (successor.left) {
        path.push({ node: successor, parent: successorParent, side: successorSide });
        successorParent = successor;
        successorSide = 'left';
        successor = successor.left;
      }
      x.k = successor.k;
      successorParent[successorSide] = successor.right;
    } else if (parent) {
      parent[side] = x.left ?? x.right;
    } else {
      this.root = x.left ?? x.right;
    }

    this.balancePath = path.reverse();
    return this.nextRotation();
  }

  nextRotation() {
    // ponytail: anteprima corretta con O(log² n) nel caso peggiore; per alberi grandi aggiornare gli antenati incrementalmente.
    for (const { node } of this.balancePath) this.update(node);
    while (this.balancePath.length) {
      const entry = this.balancePath[0];
      const factor = this.balanceFactor(entry.node);
      if (Math.abs(factor) > 1) {
        this.pendingRotation = entry;
        const type = factor > 1
          ? this.balanceFactor(entry.node.left) < 0 ? 'SD' : 'SS'
          : this.balanceFactor(entry.node.right) > 0 ? 'DS' : 'DD';
        return { key: entry.node.k, type };
      }
      this.balancePath.shift();
    }
    this.pendingRotation = null;
    return null;
  }

  continueRotation() {
    if (!this.pendingRotation) return null;
    const { node, parent, side } = this.pendingRotation;
    const balanced = this.rebalance(node);
    if (parent) parent[side] = balanced;
    else this.root = balanced;
    this.balancePath.shift();
    this.pendingRotation = null;
    return this.nextRotation();
  }
}

if (typeof module !== 'undefined') module.exports = BST;
