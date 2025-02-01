document.getElementById('generate-pdf').addEventListener('click', async () => {
  const { PDFDocument, rgb, StandardFonts } = PDFLib;

  async function generatePDF(products) {
    const pdfDoc = await PDFDocument.create();

    const boxWidthCm = 6.5;
    const boxHeightCm = 4;
    const marginCm = 0.2;

    const boxWidth = boxWidthCm * 28.35;
    const boxHeight = boxHeightCm * 28.35;
    const margin = marginCm * 28.35;

    const productsPerRow = 2;
    const totalColumns = Math.ceil(products.length / productsPerRow);

    const pageWidth = (boxWidth + margin) * totalColumns + margin;
    const pageHeight = (boxHeight + margin) * productsPerRow + margin;

    const page = pdfDoc.addPage([pageWidth, pageHeight]);

    const font = await pdfDoc.embedFont(StandardFonts.Helvetica);

    function splitTextIntoLines(text, maxWidth, fontSize) {
      const words = text.split(' ');
      let lines = [];
      let currentLine = '';

      words.forEach((word) => {
        const testLine = currentLine ? `${currentLine} ${word}` : word;
        const testWidth = font.widthOfTextAtSize(testLine, fontSize);

        if (testWidth <= maxWidth) {
          currentLine = testLine;
        } else {
          lines.push(currentLine);
          currentLine = word;
        }
      });

      if (currentLine) lines.push(currentLine);
      return lines;
    }

    products.forEach((product, index) => {
      const rowIndex = index % productsPerRow;
      const columnIndex = Math.floor(index / productsPerRow);

      const xPosition = margin + columnIndex * (boxWidth + margin);
      const yPosition = pageHeight - (rowIndex + 1) * (boxHeight + margin);

      page.drawRectangle({
        x: xPosition,
        y: yPosition,
        width: boxWidth,
        height: boxHeight,
        borderColor: rgb(0, 0, 0),
        borderWidth: 1
      });

      const nameFontSize = 12;
      const nameMarginTop = 10;
      const nameMarginBottom = 30; // Space between name and price
      const espaciado = 0.5;
      const maxTextHeight = boxHeight - nameMarginTop - nameMarginBottom;

      const lines = splitTextIntoLines(product.name, boxWidth - 20, nameFontSize);
      const totalTextHeight = -lines.length * (nameFontSize + espaciado);

      // Calculate y position for name
      let textYPosition = yPosition + boxHeight - nameMarginBottom - (totalTextHeight / 2);

      if (totalTextHeight > maxTextHeight) {
        textYPosition = yPosition + boxHeight - nameMarginBottom - (maxTextHeight / 2);
      }

      lines.forEach((line) => {
        const textWidth = font.widthOfTextAtSize(line, nameFontSize);
        const textXPosition = xPosition + (boxWidth - textWidth) / 2;

        page.drawText(line, {
          x: textXPosition,
          y: textYPosition,
          size: nameFontSize,
          font: font,
          color: rgb(0, 0, 0)
        });

        textYPosition -= nameFontSize + espaciado;
      });

      // Price with different font sizes for the currency symbol
      const priceSize = 32;
      const smallCurrencySize = 12; // Smaller font size for the currency symbol
      const priceText = `${product.price}`;

      const currencySymbol = 'S/. ';

      // Position for the currency symbol
      const currencySymbolWidth = font.widthOfTextAtSize(currencySymbol, smallCurrencySize);
      const priceWidth = currencySymbolWidth + font.widthOfTextAtSize(priceText, priceSize);
      const priceXPosition = xPosition + (boxWidth - priceWidth) / 2;
      const currencyXPosition = priceXPosition;
      const priceYPosition = yPosition + (boxHeight - priceSize) / 2;
      const currencyYPosition = -10+priceYPosition + (priceSize - smallCurrencySize) / 2;

      page.drawText(currencySymbol, {
        x: currencyXPosition,
        y: currencyYPosition,
        size: smallCurrencySize,
        font: font,
        color: rgb(0, 0, 0)
      });

      const amountXPosition = priceXPosition + currencySymbolWidth;
      page.drawText(priceText, {
        x: amountXPosition,
        y: priceYPosition,
        size: priceSize,
        font: font,
        color: rgb(0, 0, 0)
      });

      const code5Size = 10;
      const code5Text = `${product.code5}`;
      const code5YPosition = yPosition + 5;
      page.drawText(code5Text, {
        x: xPosition + 5,
        y: code5YPosition,
        size: code5Size,
        font: font,
        color: rgb(0, 0, 0)
      });

      const code13Size = 10;
      const code13Text = product.code13;
      const code13Width = font.widthOfTextAtSize(code13Text, code13Size);
      const code13XPosition = xPosition + boxWidth - code13Width - 5;
      page.drawText(code13Text, {
        x: code13XPosition,
        y: code5YPosition,
        size: code13Size,
        font: font,
        color: rgb(0, 0, 0)
      });
    });

    const pdfBytes = await pdfDoc.save();
    const blob = new Blob([pdfBytes], { type: 'application/pdf' });
    const link = document.createElement('a');
    link.href = URL.createObjectURL(blob);
    link.download = 'productos.pdf';
    document.body.appendChild(link);
    link.click();
    document.body.removeChild(link);
  }

  const products = [
    { name: 'COPAS POLIETILENO CHEMRAY X50', price: '21.50', code5: '12345', code13: '1234567890123' },
    { name: 'Producto 2', price: '13.90', code5: '67890', code13: '0987654321098' },
    { name: 'Producto 3', price: '14.50', code5: '54321', code13: '1122334455667' },
    { name: 'Producto 4', price: '14.50', code5: '98765', code13: '6677889900112' },
    { name: 'Producto 4', price: '14.50', code5: '98765', code13: '6677889900112' },
    { name: 'Producto 4', price: '14.50', code5: '98765', code13: '6677889900112' },
    { name: 'Producto 4', price: '14.50', code5: '98765', code13: '6677889900112' },
    { name: 'Producto 4', price: '14.50', code5: '98765', code13: '6677889900112' },
    { name: 'Producto 4', price: '14.50', code5: '98765', code13: '6677889900112' },
    { name: 'Producto 4', price: '14.50', code5: '98765', code13: '6677889900112' },
    { name: 'Producto 4', price: '14.50', code5: '98765', code13: '6677889900112' },
    { name: 'Producto 4', price: '14.50', code5: '98765', code13: '6677889900112' },
    { name: 'Producto 4', price: '14.50', code5: '98765', code13: '6677889900112' },
    { name: 'Producto 4', price: '14.50', code5: '98765', code13: '6677889900112' },
    { name: 'Producto 4', price: '14.50', code5: '98765', code13: '6677889900112' },
    { name: 'Producto 4', price: '14.50', code5: '98765', code13: '6677889900112' },
    { name: 'Producto 4', price: '14.50', code5: '98765', code13: '6677889900112' },
    { name: 'Producto 4', price: '14.50', code5: '98765', code13: '6677889900112' },
    { name: 'Producto 4', price: '14.50', code5: '98765', code13: '6677889900112' },
    { name: 'Producto 4', price: '14.50', code5: '98765', code13: '6677889900112' },
    { name: 'Producto 4', price: '14.50', code5: '98765', code13: '6677889900112' },
    { name: 'Producto 4', price: '14.50', code5: '98765', code13: '6677889900112' },
    { name: 'Producto 4', price: '14.50', code5: '98765', code13: '6677889900112' },
    { name: 'Producto 4', price: '14.50', code5: '98765', code13: '6677889900112' },
    { name: 'Producto 4', price: '14.50', code5: '98765', code13: '6677889900112' },
    { name: 'Producto 4', price: '14.50', code5: '98765', code13: '6677889900112' },
    { name: 'Producto 4', price: '14.50', code5: '98765', code13: '6677889900112' },
    { name: 'Producto 4', price: '14.50', code5: '98765', code13: '6677889900112' },
    { name: 'Producto 4', price: '14.50', code5: '98765', code13: '6677889900112' }
  ];

  generatePDF(products);
});