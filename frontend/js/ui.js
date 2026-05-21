/**
 * UI Controller
 * Minimalus funkcionalumas testavimui
 */

console.log('[UI] ui.js loaded');

// Laukiam kol DOM užsikraus
document.addEventListener('DOMContentLoaded', function() {
    console.log('[UI] DOM fully loaded');
    
    // Test funkcionalumas - START mygtukas
    const startBtn = document.getElementById('startBtn');
    const logContent = document.getElementById('logContent');
    
    startBtn.addEventListener('click', function() {
        const timestamp = new Date().toLocaleTimeString('lt-LT');
        logContent.textContent = `[${timestamp}] ✓ Mygtukas veikia!\n` +
                                 `HTML struktūra užsikrovė sėkmingai.\n` +
                                 `Visi JS failai prijungti.\n\n` +
                                 `COMMIT 1 testas pavyko! ✓`;
        
        console.log('[UI] Start button clicked - test successful');
    });
    
    console.log('[UI] Event listeners registered');
});
