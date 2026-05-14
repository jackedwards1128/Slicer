# ATCS Slicer

A 3D model slicer written in Java that reads binary STL files, slices them into layers, generates infill patterns, and renders a visual layer-by-layer preview.

## Overview

This program takes a 3D model in STL format and simulates the slicing process used by FDM (fused deposition modeling) 3D printers. Each layer of the model is computed as a set of line segments representing the perimeter and diagonal infill, then displayed in a simple GUI where you can browse through the layers.

## Features

- Parses binary STL files
- Slices models into configurable layers
- Generates diagonal infill patterns clipped to the model perimeter
- Interactive layer-by-layer viewer (navigate with arrow keys)

## Project Structure

| File | Description |
|------|-------------|
| `Program.java` | Entry point. Reads the STL file, orchestrates slicing, and launches the window. |
| `Slicer.java` | Core slicing logic. Iterates through each layer height, finds triangle cross-sections, and generates infill. |
| `Triangle.java` | Represents a single mesh triangle. Handles layer intersection checks and endpoint interpolation. |
| `LineSegment.java` | Represents a 2D line segment. Includes intersection math and above/below line checks used for infill clipping. |
| `Window.java` | Swing-based GUI that renders the current layer and handles keyboard input. |

## Usage

### Compile

```bash
javac *.java
```

### Run

```bash
java Program <path-to-stl-file>
```

**Example:**
```bash
java Program model.stl
```

The STL file must be in **binary STL format** (not ASCII).

### Controls

| Key | Action |
|-----|--------|
| `↑` Up Arrow | Move to the next layer up |
| `↓` Down Arrow | Move to the next layer down |

## How It Works

### 1. STL Parsing
The program reads the binary STL file byte by byte using a `ByteBuffer`. It skips the 80-byte header, reads the triangle count, then reads each triangle's three vertices (skipping the normal vector and attribute bytes).

### 2. Slicing
The `Slicer` divides the model's height into layers of a fixed height (currently `0.3` units). For each layer, it checks every triangle to see if the layer plane intersects it — a triangle intersects a layer if it has at least one vertex above and one below the layer's Z value. The two intersection points are found by linear interpolation along each crossing edge.

### 3. Infill Generation
For each layer, a set of diagonal lines is generated across the model's bounding box. Each diagonal is then clipped to the interior of the model by finding its intersections with the perimeter segments and keeping only the segments that fall inside (using alternating inside/outside logic).

### 4. Rendering
The `Window` class uses Java Swing and paints all line segments for the currently selected layer directly onto the canvas, scaled and offset to fit the view. The current layer index is displayed in the top-right.

## Configuration

A few values are currently hardcoded and can be adjusted in the source:

| Location | Variable | Default | Description |
|----------|----------|---------|-------------|
| `Program.java` | `Slicer(0.3, ...)` | `0.3` | Layer height |
| `Slicer.java` | `createInfillForLayer(..., 1, ...)` | `1` | Infill line spacing |
| `Window.java` | `shiftLayerUp/Down` clamp | `0–86` | Layer index bounds (should match model) |
| `Window.java` | draw offset | `250, 300` | Canvas offset for rendering |
| `Window.java` | draw scale | `10.0` | Scale factor for rendering |

## Known Limitations

- Only binary STL format is supported.
- Layer count upper bound in `Window.java` is hardcoded to `86` and should be updated to match the actual model height.
- Infill intersection ordering assumes intersections come in pairs; malformed or very complex perimeters may cause incorrect infill.
- No support for multiple perimeter shells or variable infill density.
